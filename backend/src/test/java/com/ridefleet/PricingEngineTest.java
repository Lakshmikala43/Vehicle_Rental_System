package com.ridefleet;

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
public class PricingEngineTest {

    @Autowired
    private PricingEngineService pricingEngineService;

    @Test
    @DisplayName("Verify Flat Coupon Deduction and Tax Calculation")
    public void testFlatCouponDeduction() {
        PricingCalculationRequest request = new PricingCalculationRequest();
        request.setVehicleId(1L);
        request.setPickupDateTime(LocalDateTime.of(2026, 9, 10, 10, 0));
        request.setReturnDateTime(LocalDateTime.of(2026, 9, 12, 10, 0));
        request.setCouponCode("FREEDOM500");

        PricingCalculationResponse response = pricingEngineService.calculatePricing(request);

        assertNotNull(response);
        assertEquals(new BigDecimal("500.00"), response.getDiscountAmount());
        assertNotNull(response.getTaxAmount());
        assertNotNull(response.getGrandTotal());
    }

    @Test
    @DisplayName("Verify Add-ons Summation with Daily Rate Duration")
    public void testAddOnsSummation() {
        PricingCalculationRequest request = new PricingCalculationRequest();
        request.setVehicleId(1L);
        request.setPickupDateTime(LocalDateTime.of(2026, 9, 10, 10, 0));
        request.setReturnDateTime(LocalDateTime.of(2026, 9, 13, 10, 0)); // 3 days
        request.setAddOnIds(List.of(1L, 2L)); // Child seat + GPS

        PricingCalculationResponse response = pricingEngineService.calculatePricing(request);

        assertNotNull(response);
        assertEquals(3, response.getTotalDays());
        assertTrue(response.getAddOnsAmount().compareTo(BigDecimal.ZERO) > 0);
    }
}
