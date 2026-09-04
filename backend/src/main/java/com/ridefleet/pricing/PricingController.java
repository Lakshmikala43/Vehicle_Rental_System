package com.ridefleet.pricing;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    @Autowired
    private PricingEngineService pricingEngineService;

    @Autowired
    private AddOnRepository addOnRepository;

    @Autowired
    private CouponRepository couponRepository;

    @PostMapping("/calculate")
    public ResponseEntity<ApiResponse<PricingCalculationResponse>> calculatePrice(@RequestBody PricingCalculationRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(pricingEngineService.calculatePricing(request)));
    }

    @GetMapping("/add-ons")
    public ResponseEntity<ApiResponse<List<AddOn>>> getActiveAddOns() {
        return ResponseEntity.ok(ApiResponse.ok(addOnRepository.findByIsActiveTrue()));
    }

    @GetMapping("/coupons/{code}")
    public ResponseEntity<ApiResponse<Coupon>> getCouponByCode(@PathVariable String code) {
        Coupon coupon = couponRepository.findByCodeAndIsActiveTrue(code.toUpperCase())
                .orElse(null);
        if (coupon == null) {
            return ResponseEntity.ok(ApiResponse.error("Invalid or expired coupon code"));
        }
        return ResponseEntity.ok(ApiResponse.ok(coupon));
    }
}
