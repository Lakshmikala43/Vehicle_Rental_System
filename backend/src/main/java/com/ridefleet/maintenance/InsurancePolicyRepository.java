package com.ridefleet.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {
    Optional<InsurancePolicy> findByVehicleId(Long vehicleId);
    List<InsurancePolicy> findByExpiryDateBefore(LocalDate date);
}
