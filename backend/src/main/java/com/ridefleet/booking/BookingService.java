package com.ridefleet.booking;

import com.ridefleet.auth.User;
import com.ridefleet.auth.UserRepository;
import com.ridefleet.branch.Branch;
import com.ridefleet.branch.BranchService;
import com.ridefleet.common.BadRequestException;
import com.ridefleet.common.ResourceNotFoundException;
import com.ridefleet.pricing.PricingCalculationRequest;
import com.ridefleet.pricing.PricingCalculationResponse;
import com.ridefleet.pricing.PricingEngineService;
import com.ridefleet.vehicle.Vehicle;
import com.ridefleet.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private PricingEngineService pricingEngineService;

    @Transactional
    public Booking createBooking(String username, BookingRequest request) {
        User customer = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Customer user not found: " + username));

        Vehicle vehicle = vehicleService.getVehicleById(request.getVehicleId());
        Branch pickupBranch = branchService.getBranchById(request.getPickupBranchId());
        Branch returnBranch = branchService.getBranchById(request.getReturnBranchId());

        // Collision Check: Prevent Double Booking!
        boolean hasOverlap = bookingRepository.existsOverlappingBooking(
                vehicle.getId(), request.getPickupDateTime(), request.getReturnDateTime());
        if (hasOverlap) {
            throw new BadRequestException("Vehicle " + vehicle.getModel().getModelName() + 
                    " (" + vehicle.getLicensePlate() + ") is already booked for the selected date and time range!");
        }

        // Calculate Pricing
        PricingCalculationRequest pricingRequest = new PricingCalculationRequest();
        pricingRequest.setVehicleId(vehicle.getId());
        pricingRequest.setPickupDateTime(request.getPickupDateTime());
        pricingRequest.setReturnDateTime(request.getReturnDateTime());
        pricingRequest.setAddOnIds(request.getAddOnIds());
        pricingRequest.setCouponCode(request.getCouponCode());

        PricingCalculationResponse pricing = pricingEngineService.calculatePricing(pricingRequest);

        Booking booking = new Booking();
        booking.setBookingReference("RF-BK-" + (100000 + new Random().nextInt(900000)));
        booking.setCustomer(customer);
        booking.setVehicle(vehicle);
        booking.setPickupBranch(pickupBranch);
        booking.setReturnBranch(returnBranch);
        booking.setPickupDateTime(request.getPickupDateTime());
        booking.setReturnDateTime(request.getReturnDateTime());
        booking.setStatus("PAYMENT_PENDING");
        booking.setBaseAmount(pricing.getBaseAmount());
        booking.setAddOnsAmount(pricing.getAddOnsAmount());
        booking.setDiscountAmount(pricing.getDiscountAmount());
        booking.setTaxAmount(pricing.getTaxAmount());
        booking.setSecurityDeposit(pricing.getSecurityDeposit());
        booking.setTotalAmount(pricing.getGrandTotal());

        return bookingRepository.save(booking);
    }

    public List<Booking> getCustomerBookings(String username) {
        User customer = userRepository.findByUsername(username).orElseThrow();
        return bookingRepository.findByCustomerIdOrderByCreatedAtDesc(customer.getId());
    }

    public Booking getBookingByReference(String reference) {
        return bookingRepository.findByBookingReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + reference));
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
    }

    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        if ("CONFIRMED".equals(status) || "ACTIVE".equals(status)) {
            vehicleService.updateVehicleStatus(booking.getVehicle().getId(), "RESERVED");
        } else if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
            vehicleService.updateVehicleStatus(booking.getVehicle().getId(), "AVAILABLE");
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
