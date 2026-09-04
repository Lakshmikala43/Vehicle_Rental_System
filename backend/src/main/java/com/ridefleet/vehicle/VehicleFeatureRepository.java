package com.ridefleet.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleFeatureRepository extends JpaRepository<VehicleFeature, Long> {
    Optional<VehicleFeature> findByFeatureCode(String featureCode);
}
