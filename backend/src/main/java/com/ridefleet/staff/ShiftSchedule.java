package com.ridefleet.staff;

import com.ridefleet.auth.User;
import com.ridefleet.branch.Branch;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift_schedules")
public class ShiftSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_user_id", nullable = false)
    private User staffUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "shift_start", nullable = false)
    private LocalDateTime shiftStart;

    @Column(name = "shift_end", nullable = false)
    private LocalDateTime shiftEnd;

    @Column(length = 20)
    private String status = "SCHEDULED"; // SCHEDULED, IN_PROGRESS, COMPLETED, ABSENT

    public ShiftSchedule() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getStaffUser() { return staffUser; }
    public void setStaffUser(User staffUser) { this.staffUser = staffUser; }
    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }
    public LocalDateTime getShiftStart() { return shiftStart; }
    public void setShiftStart(LocalDateTime shiftStart) { this.shiftStart = shiftStart; }
    public LocalDateTime getShiftEnd() { return shiftEnd; }
    public void setShiftEnd(LocalDateTime shiftEnd) { this.shiftEnd = shiftEnd; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
