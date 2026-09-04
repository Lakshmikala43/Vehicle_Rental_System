package com.ridefleet.maintenance;

import com.ridefleet.vehicle.Vehicle;
import com.ridefleet.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    @Autowired
    private VehicleService vehicleService;

    @Transactional
    public MaintenanceRecord createWorkOrder(MaintenanceRecord record) {
        record.setWorkOrderNumber("WO-RF-" + (10000 + new Random().nextInt(90000)));
        Vehicle vehicle = vehicleService.getVehicleById(record.getVehicle().getId());
        record.setVehicle(vehicle);

        // Mark vehicle as MAINTENANCE
        vehicleService.updateVehicleStatus(vehicle.getId(), "MAINTENANCE");

        return maintenanceRepository.save(record);
    }

    @Transactional
    public MaintenanceRecord completeWorkOrder(Long workOrderId) {
        MaintenanceRecord record = maintenanceRepository.findById(workOrderId).orElseThrow();
        record.setStatus("COMPLETED");
        record.setCompletionDate(LocalDate.now());

        // Restore vehicle status to AVAILABLE and health score to 100
        Vehicle vehicle = record.getVehicle();
        vehicle.setHealthScore(100);
        vehicleService.updateVehicleStatus(vehicle.getId(), "AVAILABLE");

        return maintenanceRepository.save(record);
    }

    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRepository.findAll();
    }

    public List<InsurancePolicy> getExpiringInsurancePolicies(int days) {
        return insurancePolicyRepository.findByExpiryDateBefore(LocalDate.now().plusDays(days));
    }
}
