package com.ridefleet.maintenance;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MaintenanceRecord>>> getAllMaintenance() {
        return ResponseEntity.ok(ApiResponse.ok(maintenanceService.getAllMaintenanceRecords()));
    }

    @PostMapping("/work-orders")
    public ResponseEntity<ApiResponse<MaintenanceRecord>> createWorkOrder(@RequestBody MaintenanceRecord record) {
        return ResponseEntity.ok(ApiResponse.ok("Work order logged", maintenanceService.createWorkOrder(record)));
    }

    @PatchMapping("/work-orders/{id}/complete")
    public ResponseEntity<ApiResponse<MaintenanceRecord>> completeWorkOrder(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Work order completed", maintenanceService.completeWorkOrder(id)));
    }
}
