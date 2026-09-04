package com.ridefleet.payment;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions")
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "gateway_txn_id", nullable = false, length = 100)
    private String gatewayTxnId;

    @Column(name = "response_code", length = 30)
    private String responseCode;

    @Column(name = "response_message", length = 255)
    private String responseMessage;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public PaymentTransaction() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public String getGatewayTxnId() { return gatewayTxnId; }
    public void setGatewayTxnId(String gatewayTxnId) { this.gatewayTxnId = gatewayTxnId; }
    public String getResponseCode() { return responseCode; }
    public void setResponseCode(String responseCode) { this.responseCode = responseCode; }
    public String getResponseMessage() { return responseMessage; }
    public void setResponseMessage(String responseMessage) { this.responseMessage = responseMessage; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
