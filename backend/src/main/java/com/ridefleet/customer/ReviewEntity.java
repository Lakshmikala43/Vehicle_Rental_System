package com.ridefleet.customer;

import com.ridefleet.auth.User;
import com.ridefleet.booking.Booking;
import com.ridefleet.vehicle.Vehicle;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "overall_rating", nullable = false)
    private Integer overallRating;

    @Column(name = "cleanliness_rating")
    private Integer cleanlinessRating;

    @Column(name = "comfort_rating")
    private Integer comfortRating;

    @Column(name = "service_rating")
    private Integer serviceRating;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public ReviewEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Integer getOverallRating() { return overallRating; }
    public void setOverallRating(Integer overallRating) { this.overallRating = overallRating; }
    public Integer getCleanlinessRating() { return cleanlinessRating; }
    public void setCleanlinessRating(Integer cleanlinessRating) { this.cleanlinessRating = cleanlinessRating; }
    public Integer getComfortRating() { return comfortRating; }
    public void setComfortRating(Integer comfortRating) { this.comfortRating = comfortRating; }
    public Integer getServiceRating() { return serviceRating; }
    public void setServiceRating(Integer serviceRating) { this.serviceRating = serviceRating; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
