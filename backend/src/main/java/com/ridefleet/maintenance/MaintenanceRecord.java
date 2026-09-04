package com.ridefleet.maintenance;

import com.ridefleet.vehicle.Vehicle;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_records")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_order_number", nullable = false, unique = true, length = 50)
    private String workOrderNumber;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "maintenance_type", nullable = false, length = 30)
    private String maintenanceType; // PREVENTIVE, CORRECTIVE

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "vendor_name", length = 150)
    private String vendorName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(nullable = false, length = 30)
    private String status = "IN_PROGRESS"; // IN_PROGRESS, COMPLETED, CANCELLED

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public MaintenanceRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWorkOrderNumber() { return workOrderNumber; }
    public void setWorkOrderNumber(String workOrderNumber) { this.workOrderNumber = workOrderNumber; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public String getMaintenanceType() { return maintenanceType; }
    public void setMaintenanceType(String maintenanceType) { this.maintenanceType = maintenanceType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
