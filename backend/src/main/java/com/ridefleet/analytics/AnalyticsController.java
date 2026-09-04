package com.ridefleet.analytics;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getExecutiveSummary() {
        return ResponseEntity.ok(ApiResponse.ok(analyticsService.getExecutiveDashboardSummary()));
    }
}
