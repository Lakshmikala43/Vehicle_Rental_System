package com.ridefleet.payment;

import com.ridefleet.booking.Booking;
import com.ridefleet.booking.BookingService;
import com.ridefleet.common.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class PaymentSimulationService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private BookingService bookingService;

    @Transactional
    public Payment processPayment(PaymentRequest request) {
        Booking booking = bookingService.getBookingById(request.getBookingId());

        // Test failure trigger simulation: If card ends in 9999 or UPI equals fail@upi, trigger decline
        boolean isDecline = (request.getCardNumber() != null && request.getCardNumber().endsWith("9999")) ||
                            (request.getUpiId() != null && request.getUpiId().equalsIgnoreCase("fail@upi"));

        Payment payment = new Payment();
        payment.setPaymentReference("PAY-RF-" + (10000000 + new Random().nextInt(90000000)));
        payment.setBooking(booking);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount() != null ? request.getAmount() : booking.getTotalAmount());

        if (isDecline) {
            payment.setPaymentStatus("FAILED");
            Payment savedPayment = paymentRepository.save(payment);

            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setPayment(savedPayment);
            transaction.setGatewayTxnId("TXN_DECLINED_" + System.currentTimeMillis());
            transaction.setResponseCode("ERR_INSUFFICIENT_FUNDS");
            transaction.setResponseMessage("Transaction declined by simulated issuing bank");
            paymentTransactionRepository.save(transaction);

            throw new BadRequestException("Payment failed: Simulated bank declined the transaction (Test Rule Triggered)");
        }

        payment.setPaymentStatus("SUCCESS");
        Payment savedPayment = paymentRepository.save(payment);

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setPayment(savedPayment);
        transaction.setGatewayTxnId("TXN_RF_" + System.currentTimeMillis());
        transaction.setResponseCode("00_SUCCESS");
        transaction.setResponseMessage("Simulated payment approved successfully");
        paymentTransactionRepository.save(transaction);

        // Update Booking Status to CONFIRMED
        bookingService.updateBookingStatus(booking.getId(), "CONFIRMED");

        // Automatically Generate Tax Invoice
        generateInvoice(booking);

        return savedPayment;
    }

    private Invoice generateInvoice(Booking booking) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("INV-RF-" + (10000 + new Random().nextInt(90000)));
        invoice.setBooking(booking);
        invoice.setSubtotal(booking.getBaseAmount().add(booking.getAddOnsAmount()).subtract(booking.getDiscountAmount()));
        invoice.setTaxTotal(booking.getTaxAmount());
        invoice.setGrandTotal(booking.getTotalAmount());
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceByBookingId(Long bookingId) {
        return invoiceRepository.findByBookingId(bookingId)
                .orElse(null);
    }
}
