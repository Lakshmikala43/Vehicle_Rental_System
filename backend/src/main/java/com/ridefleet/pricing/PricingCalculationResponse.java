package com.ridefleet.pricing;

import java.math.BigDecimal;

public class PricingCalculationResponse {
    private long totalHours;
    private long totalDays;
    private BigDecimal baseAmount;
    private BigDecimal addOnsAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal securityDeposit;
    private BigDecimal grandTotal;
    private boolean isWeekendSurgeApplied;

    public long getTotalHours() { return totalHours; }
    public void setTotalHours(long totalHours) { this.totalHours = totalHours; }
    public long getTotalDays() { return totalDays; }
    public void setTotalDays(long totalDays) { this.totalDays = totalDays; }
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
    public BigDecimal getGrandTotal() { return grandTotal; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    public boolean isWeekendSurgeApplied() { return isWeekendSurgeApplied; }
    public void setWeekendSurgeApplied(boolean weekendSurgeApplied) { isWeekendSurgeApplied = weekendSurgeApplied; }
}
