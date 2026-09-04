package com.ridefleet.pricing;

import java.time.LocalDateTime;
import java.util.List;

public class PricingCalculationRequest {
    private Long vehicleId;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;
    private List<Long> addOnIds;
    private String couponCode;

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public LocalDateTime getPickupDateTime() { return pickupDateTime; }
    public void setPickupDateTime(LocalDateTime pickupDateTime) { this.pickupDateTime = pickupDateTime; }
    public LocalDateTime getReturnDateTime() { return returnDateTime; }
    public void setReturnDateTime(LocalDateTime returnDateTime) { this.returnDateTime = returnDateTime; }
    public List<Long> getAddOnIds() { return addOnIds; }
    public void setAddOnIds(List<Long> addOnIds) { this.addOnIds = addOnIds; }
    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
}
