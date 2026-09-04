package com.ridefleet.inspection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleInspectionRepository extends JpaRepository<VehicleInspection, Long> {
    List<VehicleInspection> findByBookingId(Long bookingId);
    List<VehicleInspection> findByVehicleId(Long vehicleId);
}
