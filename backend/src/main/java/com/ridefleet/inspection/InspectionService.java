package com.ridefleet.inspection;

import com.ridefleet.booking.Booking;
import com.ridefleet.booking.BookingService;
import com.ridefleet.vehicle.Vehicle;
import com.ridefleet.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class InspectionService {

    @Autowired
    private VehicleInspectionRepository inspectionRepository;

    @Autowired
    private DamageReportRepository damageReportRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VehicleService vehicleService;

    @Transactional
    public VehicleInspection recordInspection(VehicleInspection inspection) {
        Booking booking = bookingService.getBookingById(inspection.getBooking().getId());
        Vehicle vehicle = vehicleService.getVehicleById(inspection.getVehicle().getId());

        inspection.setBooking(booking);
        inspection.setVehicle(vehicle);

        // Update vehicle odometer and fuel level
        if (inspection.getOdometerReading() != null && inspection.getOdometerReading() > vehicle.getOdometerKm()) {
            vehicle.setOdometerKm(inspection.getOdometerReading());
        }
        if (inspection.getFuelLevelPercent() != null) {
            vehicle.setFuelLevelPercent(inspection.getFuelLevelPercent());
        }
        vehicleService.createVehicle(vehicle);

        // Update booking status on pickup/return
        if ("PICKUP".equalsIgnoreCase(inspection.getInspectionType())) {
            bookingService.updateBookingStatus(booking.getId(), "ACTIVE");
            vehicleService.updateVehicleStatus(vehicle.getId(), "RENTED");
        } else if ("RETURN".equalsIgnoreCase(inspection.getInspectionType())) {
            bookingService.updateBookingStatus(booking.getId(), "COMPLETED");
            vehicleService.updateVehicleStatus(vehicle.getId(), "AVAILABLE");
        }

        return inspectionRepository.save(inspection);
    }

    public DamageReport reportDamage(DamageReport report) {
        report.setDamageCode("DMG-RF-" + (10000 + new Random().nextInt(90000)));
        Vehicle vehicle = vehicleService.getVehicleById(report.getVehicle().getId());
        report.setVehicle(vehicle);

        // Degrade vehicle health score if damage is severe
        if ("SEVERE".equalsIgnoreCase(report.getSeverity())) {
            vehicleService.updateVehicleStatus(vehicle.getId(), "DAMAGED");
        }

        return damageReportRepository.save(report);
    }

    public List<DamageReport> getDamageReportsByVehicle(Long vehicleId) {
        return damageReportRepository.findByVehicleId(vehicleId);
    }

    public List<VehicleInspection> getAllInspections() {
        return inspectionRepository.findAll();
    }
}
