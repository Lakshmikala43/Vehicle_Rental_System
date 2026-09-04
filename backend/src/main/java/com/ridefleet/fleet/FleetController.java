package com.ridefleet.fleet;

import com.ridefleet.branch.Branch;
import com.ridefleet.branch.BranchService;
import com.ridefleet.common.ApiResponse;
import com.ridefleet.vehicle.Vehicle;
import com.ridefleet.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/fleet")
public class FleetController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private BranchService branchService;

    @GetMapping("/telemetry")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getFleetTelemetry() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        List<Map<String, Object>> telemetryList = new ArrayList<>();

        Random random = new Random();
        for (Vehicle v : vehicles) {
            Map<String, Object> item = new HashMap<>();
            item.put("vehicleId", v.getId());
            item.put("licensePlate", v.getLicensePlate());
            item.put("modelName", v.getModel().getBrand().getName() + " " + v.getModel().getModelName());
            item.put("status", v.getStatus());

            // Coordinates near branch location
            Branch branch = v.getBranch();
            double baseLat = branch.getLatitude() != null ? branch.getLatitude() : 12.9716;
            double baseLng = branch.getLongitude() != null ? branch.getLongitude() : 77.5946;

            // Offset for moving simulation
            double latOffset = (random.nextDouble() - 0.5) * 0.05;
            double lngOffset = (random.nextDouble() - 0.5) * 0.05;

            item.put("latitude", baseLat + latOffset);
            item.put("longitude", baseLng + lngOffset);
            item.put("speedKmh", "RENTED".equals(v.getStatus()) ? 40 + random.nextInt(40) : 0);
            item.put("fuelLevelPercent", v.getFuelLevelPercent());
            item.put("healthScore", v.getHealthScore());

            telemetryList.add(item);
        }

        return ResponseEntity.ok(ApiResponse.ok(telemetryList));
    }
}
