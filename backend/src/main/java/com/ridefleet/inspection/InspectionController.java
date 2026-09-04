package com.ridefleet.inspection;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspection")
public class InspectionController {

    @Autowired
    private InspectionService inspectionService;

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleInspection>> recordInspection(@RequestBody VehicleInspection inspection) {
        return ResponseEntity.ok(ApiResponse.ok("Inspection recorded successfully", inspectionService.recordInspection(inspection)));
    }

    @PostMapping("/damage")
    public ResponseEntity<ApiResponse<DamageReport>> reportDamage(@RequestBody DamageReport damageReport) {
        return ResponseEntity.ok(ApiResponse.ok("Damage report logged", inspectionService.reportDamage(damageReport)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleInspection>>> getAllInspections() {
        return ResponseEntity.ok(ApiResponse.ok(inspectionService.getAllInspections()));
    }

    @GetMapping("/damage/vehicle/{vehicleId}")
    public ResponseEntity<ApiResponse<List<DamageReport>>> getDamageByVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(ApiResponse.ok(inspectionService.getDamageReportsByVehicle(vehicleId)));
    }
}
