package com.ridefleet.pricing;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "discount_type", nullable = false, length = 20)
    private String discountType = "PERCENTAGE"; // PERCENTAGE or FLAT

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "min_booking_amount", precision = 10, scale = 2)
    private BigDecimal minBookingAmount = BigDecimal.ZERO;

    @Column(name = "max_discount_amount", precision = 10, scale = 2)
    private BigDecimal maxDiscountAmount;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public Coupon() {}

    public Coupon(String code, String discountType, BigDecimal discountValue, BigDecimal minBookingAmount) {
        this.code = code;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minBookingAmount = minBookingAmount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public void setDiscountValue(BigDecimal discountValue) { this.discountValue = discountValue; }
    public BigDecimal getMinBookingAmount() { return minBookingAmount; }
    public void setMinBookingAmount(BigDecimal minBookingAmount) { this.minBookingAmount = minBookingAmount; }
    public BigDecimal getMaxDiscountAmount() { return maxDiscountAmount; }
    public void setMaxDiscountAmount(BigDecimal maxDiscountAmount) { this.maxDiscountAmount = maxDiscountAmount; }
    public LocalDateTime getValidUntil() { return validUntil; }
    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
