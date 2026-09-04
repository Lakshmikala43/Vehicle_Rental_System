package com.ridefleet.vehicle;

import com.ridefleet.branch.Branch;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String vin;

    @Column(name = "license_plate", nullable = false, unique = true, length = 30)
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private VehicleModel model;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(length = 50)
    private String color;

    @Column(name = "odometer_km", nullable = false)
    private Integer odometerKm = 0;

    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "daily_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @Column(name = "security_deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal securityDeposit = new BigDecimal("5000.00");

    @Column(nullable = false, length = 30)
    private String status = "AVAILABLE"; // AVAILABLE, RESERVED, RENTED, MAINTENANCE, DAMAGED, RETIRED

    @Column(name = "fuel_level_percent")
    private Integer fuelLevelPercent = 100;

    @Column(name = "health_score")
    private Integer healthScore = 100;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "vehicle_feature_map",
        joinColumns = @JoinColumn(name = "vehicle_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<VehicleFeature> features = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Vehicle() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public VehicleModel getModel() { return model; }
    public void setModel(VehicleModel model) { this.model = model; }
    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Integer getOdometerKm() { return odometerKm; }
    public void setOdometerKm(Integer odometerKm) { this.odometerKm = odometerKm; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
    public BigDecimal getDailyRate() { return dailyRate; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }
    public BigDecimal getSecurityDeposit() { return securityDeposit; }
    public void setSecurityDeposit(BigDecimal securityDeposit) { this.securityDeposit = securityDeposit; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getFuelLevelPercent() { return fuelLevelPercent; }
    public void setFuelLevelPercent(Integer fuelLevelPercent) { this.fuelLevelPercent = fuelLevelPercent; }
    public Integer getHealthScore() { return healthScore; }
    public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }
    public Set<VehicleFeature> getFeatures() { return features; }
    public void setFeatures(Set<VehicleFeature> features) { this.features = features; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
