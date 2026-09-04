package com.ridefleet.intelligence;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/intelligence")
public class IntelligenceController {

    @Autowired
    private IntelligenceService intelligenceService;

    @GetMapping("/recommendations")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getRecommendations(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal budget) {
        return ResponseEntity.ok(ApiResponse.ok(intelligenceService.getVehicleRecommendations(category, budget)));
    }

    @GetMapping("/demand/branch/{branchId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDemandForecast(@PathVariable Long branchId) {
        return ResponseEntity.ok(ApiResponse.ok(intelligenceService.predictDemandForBranch(branchId)));
    }

    @GetMapping("/maintenance-risk/vehicle/{vehicleId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMaintenanceRisk(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(ApiResponse.ok(intelligenceService.assessMaintenanceRisk(vehicleId)));
    }
}
