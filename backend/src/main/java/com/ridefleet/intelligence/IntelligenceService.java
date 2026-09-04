package com.ridefleet.intelligence;

import com.ridefleet.booking.BookingRepository;
import com.ridefleet.vehicle.Vehicle;
import com.ridefleet.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IntelligenceService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Map<String, Object>> getVehicleRecommendations(String category, BigDecimal budget) {
        List<Vehicle> availableVehicles = vehicleRepository.findByStatus("AVAILABLE");
        List<Map<String, Object>> results = new ArrayList<>();

        for (Vehicle v : availableVehicles) {
            double score = 70.0; // Base score

            if (category != null && category.equalsIgnoreCase(v.getModel().getType().getCategory())) {
                score += 20.0;
            }
            if (budget != null && v.getDailyRate().compareTo(budget) <= 0) {
                score += 10.0;
            }

            // Odometer health bonus
            if (v.getOdometerKm() < 20000) {
                score += 5.0;
            }

            score = Math.min(99.0, Math.max(50.0, score));

            Map<String, Object> map = new HashMap<>();
            map.put("vehicle", v);
            map.put("recommendationScorePercent", Math.round(score));
            results.add(map);
        }

        results.sort((a, b) -> Long.compare((Long) b.get("recommendationScorePercent"), (Long) a.get("recommendationScorePercent")));
        return results.stream().limit(6).collect(Collectors.toList());
    }

    public Map<String, Object> predictDemandForBranch(Long branchId) {
        List<Vehicle> branchVehicles = vehicleRepository.findByBranchId(branchId);
        long total = branchVehicles.size();
        long rentedCount = branchVehicles.stream().filter(v -> "RENTED".equals(v.getStatus()) || "RESERVED".equals(v.getStatus())).count();

        double occupancyRate = total > 0 ? ((double) rentedCount / total) * 100.0 : 0.0;

        String demandLevel = "MODERATE";
        double surgeMultiplier = 1.0;

        if (occupancyRate > 75.0) {
            demandLevel = "HIGH";
            surgeMultiplier = 1.25;
        } else if (occupancyRate > 50.0) {
            demandLevel = "ELEVATED";
            surgeMultiplier = 1.10;
        } else if (occupancyRate < 25.0) {
            demandLevel = "LOW";
            surgeMultiplier = 0.95;
        }

        Map<String, Object> res = new HashMap<>();
        res.put("branchId", branchId);
        res.put("totalFleet", total);
        res.put("rentedFleet", rentedCount);
        res.put("occupancyRatePercent", Math.round(occupancyRate * 10.0) / 10.0);
        res.put("demandLevel", demandLevel);
        res.put("predictedSurgeMultiplier", surgeMultiplier);

        return res;
    }

    public Map<String, Object> assessMaintenanceRisk(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();

        int odo = vehicle.getOdometerKm();
        int health = vehicle.getHealthScore();

        int riskScore = 10;
        if (odo > 50000) riskScore += 40;
        else if (odo > 25000) riskScore += 20;

        if (health < 70) riskScore += 35;
        else if (health < 85) riskScore += 15;

        String riskLevel = riskScore > 60 ? "HIGH" : (riskScore > 30 ? "MEDIUM" : "LOW");
        String recommendation = riskScore > 60 ? "Immediate Inspection & Oil Change Required" :
                               (riskScore > 30 ? "Schedule Preventive Service within 14 days" : "Fleet Status Optimal");

        Map<String, Object> map = new HashMap<>();
        map.put("vehicleId", vehicleId);
        map.put("vin", vehicle.getVin());
        map.put("licensePlate", vehicle.getLicensePlate());
        map.put("odometerKm", odo);
        map.put("healthScore", health);
        map.put("riskScorePercent", riskScore);
        map.put("riskLevel", riskLevel);
        map.put("actionRecommendation", recommendation);

        return map;
    }
}
