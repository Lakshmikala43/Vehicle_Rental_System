package com.ridefleet.inspection;

import com.ridefleet.booking.Booking;
import com.ridefleet.vehicle.Vehicle;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_inspections")
public class VehicleInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "inspection_type", nullable = false, length = 20)
    private String inspectionType; // PICKUP or RETURN

    @Column(name = "inspector_id", nullable = false)
    private Long inspectorId;

    @Column(name = "odometer_reading", nullable = false)
    private Integer odometerReading;

    @Column(name = "fuel_level_percent", nullable = false)
    private Integer fuelLevelPercent;

    @Column(name = "overall_condition", nullable = false, length = 30)
    private String overallCondition = "EXCELLENT";

    @Column(name = "inspection_time", updatable = false)
    private LocalDateTime inspectionTime = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String notes;

    public VehicleInspection() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public String getInspectionType() { return inspectionType; }
    public void setInspectionType(String inspectionType) { this.inspectionType = inspectionType; }
    public Long getInspectorId() { return inspectorId; }
    public void setInspectorId(Long inspectorId) { this.inspectorId = inspectorId; }
    public Integer getOdometerReading() { return odometerReading; }
    public void setOdometerReading(Integer odometerReading) { this.odometerReading = odometerReading; }
    public Integer getFuelLevelPercent() { return fuelLevelPercent; }
    public void setFuelLevelPercent(Integer fuelLevelPercent) { this.fuelLevelPercent = fuelLevelPercent; }
    public String getOverallCondition() { return overallCondition; }
    public void setOverallCondition(String overallCondition) { this.overallCondition = overallCondition; }
    public LocalDateTime getInspectionTime() { return inspectionTime; }
    public void setInspectionTime(LocalDateTime inspectionTime) { this.inspectionTime = inspectionTime; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
