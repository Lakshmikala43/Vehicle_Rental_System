package com.ridefleet;

import com.ridefleet.booking.BookingRequest;
import com.ridefleet.booking.BookingService;
import com.ridefleet.common.BadRequestException;
import com.ridefleet.pricing.PricingCalculationRequest;
import com.ridefleet.pricing.PricingCalculationResponse;
import com.ridefleet.pricing.PricingEngineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PricingEngineService pricingEngineService;

    @Test
    @DisplayName("Test Pricing Engine Calculation with 18% GST and Weekend Surge")
    public void testPricingEngineCalculation() {
        PricingCalculationRequest request = new PricingCalculationRequest();
        request.setVehicleId(1L);
        // Friday to Sunday (touches weekend)
        request.setPickupDateTime(LocalDateTime.of(2026, 9, 11, 10, 0));
        request.setReturnDateTime(LocalDateTime.of(2026, 9, 13, 10, 0));
        request.setCouponCode("WELCOME10");

        PricingCalculationResponse response = pricingEngineService.calculatePricing(request);

        assertNotNull(response);
        assertEquals(48, response.getTotalHours());
        assertEquals(2, response.getTotalDays());
        assertTrue(response.isWeekendSurgeApplied());
        assertTrue(response.getDiscountAmount().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(response.getGrandTotal());
    }

    @Test
    @DisplayName("Test Double-Booking Collision Prevention")
    public void testDoubleBookingCollisionPrevention() {
        LocalDateTime pickup = LocalDateTime.of(2026, 9, 15, 10, 0);
        LocalDateTime returnTime = LocalDateTime.of(2026, 9, 18, 10, 0);

        BookingRequest request1 = new BookingRequest();
        request1.setVehicleId(1L);
        request1.setPickupBranchId(1L);
        request1.setReturnBranchId(1L);
        request1.setPickupDateTime(pickup);
        request1.setReturnDateTime(returnTime);

        // First booking reservation succeeds
        bookingService.createBooking("customer", request1);

        // Overlapping second booking attempt for same vehicle ID 1L
        BookingRequest request2 = new BookingRequest();
        request2.setVehicleId(1L);
        request2.setPickupBranchId(1L);
        request2.setReturnBranchId(1L);
        request2.setPickupDateTime(pickup.plusDays(1)); // Overlaps!
        request2.setReturnDateTime(returnTime.plusDays(1));

        // Expect Exception
        assertThrows(BadRequestException.class, () -> {
            bookingService.createBooking("customer", request2);
        });
    }
}
