package com.ridefleet.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicketEntity, Long> {
    List<SupportTicketEntity> findByCustomerId(Long customerId);
    Optional<SupportTicketEntity> findByTicketNumber(String ticketNumber);
    List<SupportTicketEntity> findByStatus(String status);
}
