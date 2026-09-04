package com.ridefleet.config;

import com.ridefleet.auth.*;
import com.ridefleet.branch.Branch;
import com.ridefleet.branch.BranchRepository;
import com.ridefleet.customer.CustomerProfile;
import com.ridefleet.customer.CustomerProfileRepository;
import com.ridefleet.pricing.AddOn;
import com.ridefleet.pricing.AddOnRepository;
import com.ridefleet.pricing.Coupon;
import com.ridefleet.pricing.CouponRepository;
import com.ridefleet.vehicle.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private VehicleBrandRepository vehicleBrandRepository;

    @Autowired
    private VehicleModelRepository vehicleModelRepository;

    @Autowired
    private VehicleFeatureRepository vehicleFeatureRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AddOnRepository addOnRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() > 0) return; // Prevent double seeding

        System.out.println(">>> RideFleet: Seeding initial production sample dataset...");

        // 1. Roles
        Role roleAdmin = roleRepository.save(new Role("ADMIN", "System Administrator with unrestricted access"));
        Role roleStaff = roleRepository.save(new Role("STAFF", "Branch Operational Employee"));
        Role roleFleet = roleRepository.save(new Role("FLEET_MANAGER", "Fleet Manager Operations"));
        Role roleCustomer = roleRepository.save(new Role("CUSTOMER", "Standard Customer Account"));

        // 2. Demo Users
        User adminUser = new User("admin", "admin@ridefleet.com", passwordEncoder.encode("admin123"), "Executive Admin", "+919876543210");
        adminUser.getRoles().add(roleAdmin);
        userRepository.save(adminUser);

        User staffUser = new User("staff", "staff@ridefleet.com", passwordEncoder.encode("staff123"), "Branch Operations Officer", "+919876543211");
        staffUser.getRoles().add(roleStaff);
        userRepository.save(staffUser);

        User fleetMgr = new User("fleetmgr", "fleet@ridefleet.com", passwordEncoder.encode("fleet123"), "Fleet Operations Lead", "+919876543212");
        fleetMgr.getRoles().add(roleFleet);
        userRepository.save(fleetMgr);

        User customerUser = new User("customer", "customer@ridefleet.com", passwordEncoder.encode("customer123"), "Ananya Roy", "+919876543213");
        customerUser.getRoles().add(roleCustomer);
        User savedCust = userRepository.save(customerUser);

        CustomerProfile custProfile = new CustomerProfile();
        custProfile.setUser(savedCust);
        custProfile.setDriverLicenseNumber("DL-KA01-2024090401");
        custProfile.setCity("Bangalore");
        custProfile.setState("Karnataka");
        custProfile.setMembershipTier("GOLD");
        customerProfileRepository.save(custProfile);

        // 3. Branches
        Branch b1 = branchRepository.save(new Branch("BR-BLR-01", "Central Metro Branch", "MG Road, Brigade Junction", "Bangalore", "Karnataka", 12.9716, 77.5946));
        Branch b2 = branchRepository.save(new Branch("BR-BLR-02", "Airport Terminal 2 Branch", "KIA Terminal 2 Pickup Hub", "Bangalore", "Karnataka", 13.1986, 77.7066));
        Branch b3 = branchRepository.save(new Branch("BR-HYD-01", "Gachibowli Financial Hub", "Financial District Main Rd", "Hyderabad", "Telangana", 17.4401, 78.3489));
        Branch b4 = branchRepository.save(new Branch("BR-MUM-01", "Bandra Kurla Hub", "BKC G Block, Bandra East", "Mumbai", "Maharashtra", 19.0600, 72.8680));

        // 4. Vehicle Types
        VehicleType tSUV = vehicleTypeRepository.save(new VehicleType("SUV", "Sports Utility Vehicle", "SUV", "Spacious 7-seater for family & long trips"));
        VehicleType tSedan = vehicleTypeRepository.save(new VehicleType("SEDAN", "Premium Executive Sedan", "Sedan", "Comfortable 5-seater for executive highway travel"));
        VehicleType tEV = vehicleTypeRepository.save(new VehicleType("EV", "Electric Vehicle", "Electric", "Zero-emission electric vehicle with long range battery"));
        VehicleType tLuxury = vehicleTypeRepository.save(new VehicleType("LUXURY", "Luxury Performance", "Luxury", "Top-tier luxury vehicles for premium luxury experiences"));
        VehicleType tHatch = vehicleTypeRepository.save(new VehicleType("HATCH", "City Hatchback", "Hatchback", "Compact city hatchback for effortless parking"));

        // 5. Brands
        VehicleBrand bToyota = vehicleBrandRepository.save(new VehicleBrand("Toyota", "Japan"));
        VehicleBrand bBMW = vehicleBrandRepository.save(new VehicleBrand("BMW", "Germany"));
        VehicleBrand bTesla = vehicleBrandRepository.save(new VehicleBrand("Tesla", "USA"));
        VehicleBrand bHyundai = vehicleBrandRepository.save(new VehicleBrand("Hyundai", "South Korea"));
        VehicleBrand bTata = vehicleBrandRepository.save(new VehicleBrand("Tata Motors", "India"));

        // 6. Models
        VehicleModel mInnova = vehicleModelRepository.save(new VehicleModel(bToyota, tSUV, "Innova Crysta VX", 2024, 7, "DIESEL", "AUTOMATIC"));
        VehicleModel mBMW5 = vehicleModelRepository.save(new VehicleModel(bBMW, tLuxury, "5 Series 530i", 2023, 5, "PETROL", "AUTOMATIC"));
        VehicleModel mTesla3 = vehicleModelRepository.save(new VehicleModel(bTesla, tEV, "Model 3 Long Range", 2024, 5, "ELECTRIC", "AUTOMATIC"));
        VehicleModel mCreta = vehicleModelRepository.save(new VehicleModel(bHyundai, tSUV, "Creta SX(O)", 2024, 5, "PETROL", "MANUAL"));
        VehicleModel mNexonEV = vehicleModelRepository.save(new VehicleModel(bTata, tEV, "Nexon EV Empowered+", 2024, 5, "ELECTRIC", "AUTOMATIC"));

        // 7. Features
        VehicleFeature fGPS = vehicleFeatureRepository.save(new VehicleFeature("GPS", "GPS Navigation", "compass"));
        VehicleFeature fSunroof = vehicleFeatureRepository.save(new VehicleFeature("SUNROOF", "Panaromic Sunroof", "sun"));
        VehicleFeature fLeather = vehicleFeatureRepository.save(new VehicleFeature("LEATHER", "Leather Interior Seats", "armchair"));
        VehicleFeature fCam = vehicleFeatureRepository.save(new VehicleFeature("CAM360", "360-degree Parking Camera", "camera"));

        Set<VehicleFeature> fullFeatures = new HashSet<>(Arrays.asList(fGPS, fSunroof, fLeather, fCam));

        // 8. Sample Vehicles Fleet
        List<Branch> branches = Arrays.asList(b1, b2, b3, b4);
        List<VehicleModel> models = Arrays.asList(mInnova, mBMW5, mTesla3, mCreta, mNexonEV);

        String[] colors = {"Graphite Black", "Ivory Pearl", "Copper Amber", "Matte Silver", "Deep Charcoal"};
        Random rng = new Random(42);

        for (int i = 1; i <= 25; i++) {
            Vehicle v = new Vehicle();
            v.setVin("RFVIN" + (10000000 + i));
            v.setLicensePlate("KA-01-RF-" + (1000 + i));
            v.setModel(models.get(i % models.size()));
            v.setBranch(branches.get(i % branches.size()));
            v.setColor(colors[i % colors.length]);
            v.setOdometerKm(5000 + rng.nextInt(45000));
            v.setHourlyRate(new BigDecimal(150 + (i % 5) * 50));
            v.setDailyRate(new BigDecimal(2500 + (i % 5) * 800));
            v.setSecurityDeposit(new BigDecimal("5000.00"));
            v.setStatus(i % 7 == 0 ? "MAINTENANCE" : (i % 5 == 0 ? "RENTED" : "AVAILABLE"));
            v.setFuelLevelPercent(80 + rng.nextInt(20));
            v.setHealthScore(i % 7 == 0 ? 65 : 95);
            v.setFeatures(fullFeatures);

            vehicleRepository.save(v);
        }

        // 9. Coupons
        couponRepository.save(new Coupon("WELCOME10", "PERCENTAGE", new BigDecimal("10.00"), new BigDecimal("1000.00")));
        couponRepository.save(new Coupon("FESTIVE20", "PERCENTAGE", new BigDecimal("20.00"), new BigDecimal("3000.00")));
        couponRepository.save(new Coupon("FREEDOM500", "FLAT", new BigDecimal("500.00"), new BigDecimal("2000.00")));

        // 10. Add-ons
        addOnRepository.save(new AddOn("ADD_CHILD_SEAT", "Infant Safety Child Seat", "ISOFIX certified premium child seat", new BigDecimal("350.00")));
        addOnRepository.save(new AddOn("ADD_GPS_HW", "Portable GPS Unit", "High precision standalone GPS tracker", new BigDecimal("200.00")));
        addOnRepository.save(new AddOn("ADD_INS_ZERO", "Zero-Depreciation Insurance Shield", "Complete coverage with zero customer liability", new BigDecimal("650.00")));
        addOnRepository.save(new AddOn("ADD_ROADSIDE", "24/7 Roadside Emergency Assistance", "Priority towing & battery jumpstart support", new BigDecimal("150.00")));

        System.out.println(">>> RideFleet: Sample database successfully initialized with 25 vehicles, 4 branches, roles, coupons, and add-ons!");
    }
}
