package com.ridefleet.inspection;

import com.ridefleet.booking.Booking;
import com.ridefleet.vehicle.Vehicle;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "damage_reports")
public class DamageReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "damage_code", nullable = false, unique = true, length = 50)
    private String damageCode;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(nullable = false, length = 30)
    private String severity; // MINOR, MAJOR, SEVERE

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "estimated_repair_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedRepairCost;

    @Column(nullable = false, length = 30)
    private String status = "REPORTED"; // REPORTED, UNDER_REVIEW, APPROVED, RESOLVED

    @Column(name = "reported_at", updatable = false)
    private LocalDateTime reportedAt = LocalDateTime.now();

    public DamageReport() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDamageCode() { return damageCode; }
    public void setDamageCode(String damageCode) { this.damageCode = damageCode; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getEstimatedRepairCost() { return estimatedRepairCost; }
    public void setEstimatedRepairCost(BigDecimal estimatedRepairCost) { this.estimatedRepairCost = estimatedRepairCost; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getReportedAt() { return reportedAt; }
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }
}
