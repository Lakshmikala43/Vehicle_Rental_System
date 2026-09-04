package com.ridefleet.pricing;

import com.ridefleet.common.BadRequestException;
import com.ridefleet.vehicle.Vehicle;
import com.ridefleet.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PricingEngineService {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AddOnRepository addOnRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Value("${ridefleet.pricing.default-tax-percent:18.0}")
    private double defaultTaxPercent;

    @Value("${ridefleet.pricing.weekend-multiplier:1.15}")
    private double weekendMultiplier;

    public PricingCalculationResponse calculatePricing(PricingCalculationRequest request) {
        if (request.getPickupDateTime() == null || request.getReturnDateTime() == null) {
            throw new BadRequestException("Pickup and Return date-times are required");
        }
        if (request.getReturnDateTime().isBefore(request.getPickupDateTime().plusHours(1))) {
            throw new BadRequestException("Rental duration must be at least 1 hour");
        }

        Vehicle vehicle = vehicleService.getVehicleById(request.getVehicleId());

        Duration duration = Duration.between(request.getPickupDateTime(), request.getReturnDateTime());
        long totalHours = Math.max(1, duration.toHours());
        long totalDays = (long) Math.ceil(totalHours / 24.0);
        if (totalDays == 0) totalDays = 1;

        // Base calculation
        BigDecimal baseAmount;
        if (totalHours < 24) {
            baseAmount = vehicle.getHourlyRate().multiply(BigDecimal.valueOf(totalHours));
        } else {
            baseAmount = vehicle.getDailyRate().multiply(BigDecimal.valueOf(totalDays));
        }

        // Weekend Surge Check
        boolean isWeekend = touchesWeekend(request.getPickupDateTime(), request.getReturnDateTime());
        if (isWeekend) {
            baseAmount = baseAmount.multiply(BigDecimal.valueOf(weekendMultiplier)).setScale(2, RoundingMode.HALF_UP);
        }

        // Add-ons calculation
        BigDecimal addOnsAmount = BigDecimal.ZERO;
        if (request.getAddOnIds() != null && !request.getAddOnIds().isEmpty()) {
            List<AddOn> addOns = addOnRepository.findAllById(request.getAddOnIds());
            for (AddOn addOn : addOns) {
                addOnsAmount = addOnsAmount.add(addOn.getPricePerDay().multiply(BigDecimal.valueOf(totalDays)));
            }
        }

        BigDecimal subtotalBeforeDiscount = baseAmount.add(addOnsAmount);

        // Coupon Discount
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (request.getCouponCode() != null && !request.getCouponCode().trim().isEmpty()) {
            Optional<Coupon> couponOpt = couponRepository.findByCodeAndIsActiveTrue(request.getCouponCode().trim().toUpperCase());
            if (couponOpt.isPresent()) {
                Coupon coupon = couponOpt.get();
                if (subtotalBeforeDiscount.compareTo(coupon.getMinBookingAmount()) >= 0) {
                    if ("PERCENTAGE".equalsIgnoreCase(coupon.getDiscountType())) {
                        discountAmount = subtotalBeforeDiscount.multiply(coupon.getDiscountValue())
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        if (coupon.getMaxDiscountAmount() != null && discountAmount.compareTo(coupon.getMaxDiscountAmount()) > 0) {
                            discountAmount = coupon.getMaxDiscountAmount();
                        }
                    } else { // FLAT
                        discountAmount = coupon.getDiscountValue();
                    }
                }
            }
        }

        BigDecimal discountedSubtotal = subtotalBeforeDiscount.subtract(discountAmount);
        if (discountedSubtotal.compareTo(BigDecimal.ZERO) < 0) {
            discountedSubtotal = BigDecimal.ZERO;
        }

        // Tax Calculation (18% GST)
        BigDecimal taxAmount = discountedSubtotal.multiply(BigDecimal.valueOf(defaultTaxPercent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal securityDeposit = vehicle.getSecurityDeposit() != null ? vehicle.getSecurityDeposit() : new BigDecimal("5000.00");
        BigDecimal grandTotal = discountedSubtotal.add(taxAmount).add(securityDeposit);

        PricingCalculationResponse response = new PricingCalculationResponse();
        response.setTotalHours(totalHours);
        response.setTotalDays(totalDays);
        response.setBaseAmount(baseAmount);
        response.setAddOnsAmount(addOnsAmount);
        response.setDiscountAmount(discountAmount);
        response.setTaxAmount(taxAmount);
        response.setSecurityDeposit(securityDeposit);
        response.setGrandTotal(grandTotal);
        response.setWeekendSurgeApplied(isWeekend);

        return response;
    }

    private boolean touchesWeekend(LocalDateTime start, LocalDateTime end) {
        LocalDateTime curr = start;
        while (curr.isBefore(end)) {
            DayOfWeek day = curr.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                return true;
            }
            curr = curr.plusHours(6);
        }
        return false;
    }
}
