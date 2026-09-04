package com.ridefleet.branch;

import com.ridefleet.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Branch>>> getAllBranches() {
        return ResponseEntity.ok(ApiResponse.ok(branchService.getAllBranches()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(branchService.getBranchById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Branch>> createBranch(@RequestBody Branch branch) {
        return ResponseEntity.ok(ApiResponse.ok("Branch created successfully", branchService.createBranch(branch)));
    }
}
