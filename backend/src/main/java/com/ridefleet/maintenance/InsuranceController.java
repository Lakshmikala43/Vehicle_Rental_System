package com.ridefleet.maintenance;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InsurancePolicy>>> getAllPolicies() {
        return ResponseEntity.ok(ApiResponse.ok(insurancePolicyRepository.findAll()));
    }

    @GetMapping("/expiring")
    public ResponseEntity<ApiResponse<List<InsurancePolicy>>> getExpiringPolicies(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(ApiResponse.ok(maintenanceService.getExpiringInsurancePolicies(days)));
    }
}
