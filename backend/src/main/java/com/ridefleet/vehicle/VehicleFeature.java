package com.ridefleet.vehicle;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_features")
public class VehicleFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feature_code", nullable = false, unique = true, length = 50)
    private String featureCode;

    @Column(name = "feature_name", nullable = false, length = 100)
    private String featureName;

    @Column(name = "icon_name", length = 50)
    private String iconName;

    public VehicleFeature() {}

    public VehicleFeature(String featureCode, String featureName, String iconName) {
        this.featureCode = featureCode;
        this.featureName = featureName;
        this.iconName = iconName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFeatureCode() { return featureCode; }
    public void setFeatureCode(String featureCode) { this.featureCode = featureCode; }
    public String getFeatureName() { return featureName; }
    public void setFeatureName(String featureName) { this.featureName = featureName; }
    public String getIconName() { return iconName; }
    public void setIconName(String iconName) { this.iconName = iconName; }
}
