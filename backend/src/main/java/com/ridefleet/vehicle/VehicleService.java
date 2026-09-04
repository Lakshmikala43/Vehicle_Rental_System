package com.ridefleet.vehicle;

import com.ridefleet.common.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));
    }

    public List<Vehicle> searchVehicles(Long branchId, String category, String fuelType, String transmission, BigDecimal maxDailyRate, String status) {
        Specification<Vehicle> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (branchId != null) {
                predicates.add(cb.equal(root.get("branch").get("id"), branchId));
            }
            if (category != null && !category.isEmpty()) {
                predicates.add(cb.equal(root.get("model").get("type").get("category"), category));
            }
            if (fuelType != null && !fuelType.isEmpty()) {
                predicates.add(cb.equal(root.get("model").get("fuelType"), fuelType));
            }
            if (transmission != null && !transmission.isEmpty()) {
                predicates.add(cb.equal(root.get("model").get("transmissionType"), transmission));
            }
            if (maxDailyRate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dailyRate"), maxDailyRate));
            }
            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return vehicleRepository.findAll(spec);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicleStatus(Long id, String status) {
        Vehicle vehicle = getVehicleById(id);
        vehicle.setStatus(status);
        return vehicleRepository.save(vehicle);
    }
}
