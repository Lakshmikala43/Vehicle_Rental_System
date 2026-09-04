package com.ridefleet.driver;

import com.ridefleet.auth.User;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "badge_number", nullable = false, unique = true, length = 50)
    private String badgeNumber;

    @Column(name = "license_category", nullable = false, length = 50)
    private String licenseCategory;

    @Column(name = "experience_years")
    private Integer experienceYears = 1;

    private Double rating = 5.0;

    @Column(name = "availability_status")
    private String availabilityStatus = "AVAILABLE"; // AVAILABLE, ON_TRIP, OFF_DUTY

    @Column(name = "hourly_rate", precision = 10, scale = 2)
    private BigDecimal hourlyRate = new BigDecimal("250.00");

    public Driver() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getBadgeNumber() { return badgeNumber; }
    public void setBadgeNumber(String badgeNumber) { this.badgeNumber = badgeNumber; }
    public String getLicenseCategory() { return licenseCategory; }
    public void setLicenseCategory(String licenseCategory) { this.licenseCategory = licenseCategory; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
}
