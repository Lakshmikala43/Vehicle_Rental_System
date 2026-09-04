package com.ridefleet.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftScheduleRepository extends JpaRepository<ShiftSchedule, Long> {
    List<ShiftSchedule> findByStaffUserId(Long staffUserId);
    List<ShiftSchedule> findByBranchId(Long branchId);
}
