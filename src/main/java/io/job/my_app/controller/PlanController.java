package io.job.my_app.controller;

import io.job.my_app.dto.PlanDto;
import io.job.my_app.exception.PlanNotFoundException;
import io.job.my_app.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    // Create a new Plan
    @PostMapping
    public ResponseEntity<PlanDto> createPlan(@RequestBody PlanDto planDto) {
        try {
            PlanDto newPlan = planService.createPlan(planDto);
            return ResponseEntity.ok(newPlan);
        } catch (Exception e) {
            // Global exception handling
            throw new RuntimeException("Error creating the plan", e);
        }
    }

    // Get all Plans in descending order
    @GetMapping
    public ResponseEntity<List<PlanDto>> getAllPlans() {
        try {
            List<PlanDto> plans = planService.getAllPlans();
            plans.sort((p1, p2) -> p2.getPlanId().compareTo(p1.getPlanId()));  // Sort by ID in descending order
            return ResponseEntity.ok(plans);
        } catch (Exception e) {
            // Global exception handling
            throw new RuntimeException("Error fetching all plans", e);
        }
    }

    // Get a Plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<PlanDto> getPlanById(@PathVariable Integer id) {
        Optional<PlanDto> plan = planService.getPlanById(id);
        return plan.map(ResponseEntity::ok)
                .orElseThrow(() -> new PlanNotFoundException("Plan with ID " + id + " not found"));
    }

    // Update a Plan by ID
    @PutMapping("/{id}")
    public ResponseEntity<PlanDto> updatePlan(@PathVariable Integer id, @RequestBody PlanDto planDto) {
        PlanDto updatedPlan = planService.updatePlan(id, planDto);
        if (updatedPlan != null) {
            return ResponseEntity.ok(updatedPlan);
        } else {
            throw new PlanNotFoundException("Plan with ID " + id + " not found for update");
        }
    }

    // Delete a Plan by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
        Optional<PlanDto> plan = planService.getPlanById(id);
        if (plan.isPresent()) {
            planService.deletePlan(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new PlanNotFoundException("Plan with ID " + id + " not found for deletion");
        }
    }
}

