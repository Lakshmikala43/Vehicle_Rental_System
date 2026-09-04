package com.ridefleet.vehicle;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Vehicle>>> searchVehicles(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String transmission,
            @RequestParam(required = false) BigDecimal maxDailyRate,
            @RequestParam(required = false, defaultValue = "AVAILABLE") String status) {
        
        List<Vehicle> vehicles = vehicleService.searchVehicles(branchId, category, fuelType, transmission, maxDailyRate, status);
        return ResponseEntity.ok(ApiResponse.ok(vehicles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vehicle>> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(vehicleService.getVehicleById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Vehicle>> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(ApiResponse.ok("Vehicle added to fleet successfully", vehicleService.createVehicle(vehicle)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Vehicle>> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.ok("Vehicle status updated", vehicleService.updateVehicleStatus(id, status)));
    }
}
