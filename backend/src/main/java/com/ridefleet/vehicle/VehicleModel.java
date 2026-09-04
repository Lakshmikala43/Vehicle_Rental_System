package com.ridefleet.vehicle;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_models")
public class VehicleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private VehicleBrand brand;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private VehicleType type;

    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;

    @Column(name = "model_year", nullable = false)
    private Integer modelYear;

    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity;

    @Column(name = "fuel_type", nullable = false, length = 30)
    private String fuelType;

    @Column(name = "transmission_type", nullable = false, length = 30)
    private String transmissionType;

    public VehicleModel() {}

    public VehicleModel(VehicleBrand brand, VehicleType type, String modelName, Integer modelYear, Integer seatingCapacity, String fuelType, String transmissionType) {
        this.brand = brand;
        this.type = type;
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.seatingCapacity = seatingCapacity;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public VehicleBrand getBrand() { return brand; }
    public void setBrand(VehicleBrand brand) { this.brand = brand; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public Integer getModelYear() { return modelYear; }
    public void setModelYear(Integer modelYear) { this.modelYear = modelYear; }
    public Integer getSeatingCapacity() { return seatingCapacity; }
    public void setSeatingCapacity(Integer seatingCapacity) { this.seatingCapacity = seatingCapacity; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public String getTransmissionType() { return transmissionType; }
    public void setTransmissionType(String transmissionType) { this.transmissionType = transmissionType; }
}
