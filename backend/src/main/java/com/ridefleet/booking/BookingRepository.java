package com.ridefleet.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingReference(String bookingReference);

    List<Booking> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    List<Booking> findByVehicleId(Long vehicleId);

    List<Booking> findByStatus(String status);

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.vehicle.id = :vehicleId " +
           "AND b.status IN ('CONFIRMED', 'ACTIVE', 'READY_FOR_PICKUP', 'RESERVED') " +
           "AND b.pickupDateTime < :returnDateTime AND b.returnDateTime > :pickupDateTime")
    boolean existsOverlappingBooking(
            @Param("vehicleId") Long vehicleId,
            @Param("pickupDateTime") LocalDateTime pickupDateTime,
            @Param("returnDateTime") LocalDateTime returnDateTime
    );
}
