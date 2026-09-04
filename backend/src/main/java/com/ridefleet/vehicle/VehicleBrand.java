package com.ridefleet.vehicle;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_brands")
public class VehicleBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "origin_country", length = 100)
    private String originCountry;

    @Column(name = "logo_url")
    private String logoUrl;

    public VehicleBrand() {}

    public VehicleBrand(String name, String originCountry) {
        this.name = name;
        this.originCountry = originCountry;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOriginCountry() { return originCountry; }
    public void setOriginCountry(String originCountry) { this.originCountry = originCountry; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
}
