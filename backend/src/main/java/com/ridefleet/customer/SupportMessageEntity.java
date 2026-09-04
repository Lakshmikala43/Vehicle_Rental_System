package com.ridefleet.customer;

import com.ridefleet.auth.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "support_messages")
public class SupportMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private SupportTicketEntity ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "sent_at", updatable = false)
    private LocalDateTime sentAt = LocalDateTime.now();

    public SupportMessageEntity() {}

    public SupportMessageEntity(SupportTicketEntity ticket, User sender, String message) {
        this.ticket = ticket;
        this.sender = sender;
        this.message = message;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SupportTicketEntity getTicket() { return ticket; }
    public void setTicket(SupportTicketEntity ticket) { this.ticket = ticket; }
    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}
