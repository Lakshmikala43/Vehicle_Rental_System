package com.ridefleet;

import com.ridefleet.inspection.InspectionService;
import com.ridefleet.inspection.VehicleInspection;
import com.ridefleet.inspection.DamageReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class InspectionServiceTest {

    @Autowired
    private InspectionService inspectionService;

    @Test
    @DisplayName("Verify Pickup Inspection Recording")
    public void testPickupInspectionRecording() {
        VehicleInspection inspection = new VehicleInspection();
        inspection.setInspectionType("PICKUP");
        inspection.setOdometerReading(14500);
        inspection.setFuelLevelPercent(95);
        inspection.setOverallCondition("EXCELLENT");

        VehicleInspection saved = inspectionService.recordInspection(inspection);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("PICKUP", saved.getInspectionType());
    }

    @Test
    @DisplayName("Verify Damage Report Logging and Cost Estimation")
    public void testDamageReportLogging() {
        DamageReport report = new DamageReport();
        report.setSeverity("MAJOR");
        report.setEstimatedRepairCost(new java.math.BigDecimal("4500.00"));
        report.setDescription("Hood scratch logged during return check-in");

        DamageReport saved = inspectionService.logDamageReport(report);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("MAJOR", saved.getSeverity());
    }
}
