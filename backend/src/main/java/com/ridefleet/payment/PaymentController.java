package com.ridefleet.payment;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentSimulationService paymentSimulationService;

    @PostMapping("/process")
    public ResponseEntity<ApiResponse<Payment>> processPayment(@RequestBody PaymentRequest request) {
        Payment payment = paymentSimulationService.processPayment(request);
        return ResponseEntity.ok(ApiResponse.ok("Payment processed successfully (Simulated)", payment));
    }

    @GetMapping("/invoices/booking/{bookingId}")
    public ResponseEntity<ApiResponse<Invoice>> getInvoice(@PathVariable Long bookingId) {
        Invoice invoice = paymentSimulationService.getInvoiceByBookingId(bookingId);
        if (invoice == null) {
            return ResponseEntity.ok(ApiResponse.error("Invoice not generated yet"));
        }
        return ResponseEntity.ok(ApiResponse.ok(invoice));
    }
}
