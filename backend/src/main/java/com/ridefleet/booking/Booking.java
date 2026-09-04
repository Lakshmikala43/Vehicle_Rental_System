package com.ridefleet.booking;

import com.ridefleet.auth.User;
import com.ridefleet.branch.Branch;
import com.ridefleet.vehicle.Vehicle;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_reference", nullable = false, unique = true, length = 50)
    private String bookingReference;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "pickup_branch_id", nullable = false)
    private Branch pickupBranch;

    @ManyToOne
    @JoinColumn(name = "return_branch_id", nullable = false)
    private Branch returnBranch;

    @Column(name = "pickup_date_time", nullable = false)
    private LocalDateTime pickupDateTime;

    @Column(name = "return_date_time", nullable = false)
    private LocalDateTime returnDateTime;

    @Column(nullable = false, length = 30)
    private String status = "PENDING"; // PENDING, PAYMENT_PENDING, CONFIRMED, READY_FOR_PICKUP, ACTIVE, COMPLETED, CANCELLED

    @Column(name = "base_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseAmount;

    @Column(name = "add_ons_amount", precision = 10, scale = 2)
    private BigDecimal addOnsAmount = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "security_deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal securityDeposit;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Booking() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Branch getPickupBranch() { return pickupBranch; }
    public void setPickupBranch(Branch pickupBranch) { this.pickupBranch = pickupBranch; }
    public Branch getReturnBranch() { return returnBranch; }
    public void setReturnBranch(Branch returnBranch) { this.returnBranch = returnBranch; }
    public LocalDateTime getPickupDateTime() { return pickupDateTime; }
    public void setPickupDateTime(LocalDateTime pickupDateTime) { this.pickupDateTime = pickupDateTime; }
    public LocalDateTime getReturnDateTime() { return returnDateTime; }
    public void setReturnDateTime(LocalDateTime returnDateTime) { this.returnDateTime = returnDateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getBaseAmount() { return baseAmount; }
    public void setBaseAmount(BigDecimal baseAmount) { this.baseAmount = baseAmount; }
    public BigDecimal getAddOnsAmount() { return addOnsAmount; }
    public void setAddOnsAmount(BigDecimal addOnsAmount) { this.addOnsAmount = addOnsAmount; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getSecurityDeposit() { return securityDeposit; }
    public void setSecurityDeposit(BigDecimal securityDeposit) { this.securityDeposit = securityDeposit; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
