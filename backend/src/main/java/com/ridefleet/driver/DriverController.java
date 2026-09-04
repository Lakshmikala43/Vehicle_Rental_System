package com.ridefleet.driver;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Driver>>> getAllDrivers() {
        return ResponseEntity.ok(ApiResponse.ok(driverService.getAllDrivers()));
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<Driver>>> getAvailableDrivers() {
        return ResponseEntity.ok(ApiResponse.ok(driverService.getAvailableDrivers()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Driver>> createDriver(@RequestBody Driver driver) {
        return ResponseEntity.ok(ApiResponse.ok("Driver profile created", driverService.createDriver(driver)));
    }
}
