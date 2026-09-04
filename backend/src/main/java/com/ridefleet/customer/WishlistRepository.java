package com.ridefleet.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    List<WishlistEntity> findByCustomerId(Long customerId);
    Boolean existsByCustomerIdAndVehicleId(Long customerId, Long vehicleId);
    void deleteByCustomerIdAndVehicleId(Long customerId, Long vehicleId);
}
