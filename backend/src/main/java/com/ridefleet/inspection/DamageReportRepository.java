package com.ridefleet.inspection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamageReportRepository extends JpaRepository<DamageReport, Long> {
    List<DamageReport> findByVehicleId(Long vehicleId);
    List<DamageReport> findByBookingId(Long bookingId);
}
