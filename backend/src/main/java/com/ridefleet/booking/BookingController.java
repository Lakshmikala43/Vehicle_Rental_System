package com.ridefleet.booking;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(Authentication auth, @RequestBody BookingRequest request) {
        String username = auth.getName();
        Booking booking = bookingService.createBooking(username, request);
        return ResponseEntity.ok(ApiResponse.ok("Booking reserved successfully", booking));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<ApiResponse<List<Booking>>> getMyBookings(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.ok(bookingService.getCustomerBookings(auth.getName())));
    }

    @GetMapping("/{reference}")
    public ResponseEntity<ApiResponse<Booking>> getBookingByReference(@PathVariable String reference) {
        return ResponseEntity.ok(ApiResponse.ok(bookingService.getBookingByReference(reference)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getAllBookings() {
        return ResponseEntity.ok(ApiResponse.ok(bookingService.getAllBookings()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Booking>> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.ok("Booking status updated", bookingService.updateBookingStatus(id, status)));
    }
}
