package com.ridefleet.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    Optional<Vehicle> findByVin(String vin);
    List<Vehicle> findByBranchId(Long branchId);
    List<Vehicle> findByStatus(String status);
}
