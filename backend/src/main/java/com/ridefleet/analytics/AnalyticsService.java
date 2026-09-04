package com.ridefleet.analytics;

import com.ridefleet.booking.Booking;
import com.ridefleet.booking.BookingRepository;
import com.ridefleet.vehicle.Vehicle;
import com.ridefleet.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Map<String, Object> getExecutiveDashboardSummary() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        List<Booking> allBookings = bookingRepository.findAll();

        long totalFleet = allVehicles.size();
        long available = allVehicles.stream().filter(v -> "AVAILABLE".equalsIgnoreCase(v.getStatus())).count();
        long rented = allVehicles.stream().filter(v -> "RENTED".equalsIgnoreCase(v.getStatus()) || "RESERVED".equalsIgnoreCase(v.getStatus())).count();
        long maintenance = allVehicles.stream().filter(v -> "MAINTENANCE".equalsIgnoreCase(v.getStatus())).count();
        long damaged = allVehicles.stream().filter(v -> "DAMAGED".equalsIgnoreCase(v.getStatus())).count();

        BigDecimal totalRevenue = allBookings.stream()
                .filter(b -> !"CANCELLED".equalsIgnoreCase(b.getStatus()) && !"PENDING".equalsIgnoreCase(b.getStatus()))
                .map(Booking::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double utilizationRate = totalFleet > 0 ? ((double) rented / totalFleet) * 100.0 : 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalVehicles", totalFleet);
        summary.put("availableVehicles", available);
        summary.put("rentedVehicles", rented);
        summary.put("maintenanceVehicles", maintenance);
        summary.put("damagedVehicles", damaged);
        summary.put("totalBookingsCount", allBookings.size());
        summary.put("totalRevenue", totalRevenue);
        summary.put("fleetUtilizationPercent", Math.round(utilizationRate * 10.0) / 10.0);

        return summary;
    }
}
