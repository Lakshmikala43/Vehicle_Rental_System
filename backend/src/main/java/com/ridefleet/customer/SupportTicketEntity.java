package com.ridefleet.customer;

import com.ridefleet.auth.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "support_tickets")
public class SupportTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number", nullable = false, unique = true, length = 50)
    private String ticketNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 20)
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH, URGENT

    @Column(length = 20)
    private String status = "OPEN"; // OPEN, ASSIGNED, RESOLVED, CLOSED

    @ManyToOne
    @JoinColumn(name = "assigned_staff_id")
    private User assignedStaff;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public SupportTicketEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }
    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getAssignedStaff() { return assignedStaff; }
    public void setAssignedStaff(User assignedStaff) { this.assignedStaff = assignedStaff; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
