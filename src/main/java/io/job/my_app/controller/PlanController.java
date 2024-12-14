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


    @PostMapping
    public ResponseEntity<PlanDto> createPlan(@RequestBody PlanDto planDto) {
        try {
            PlanDto newPlan = planService.createPlan(planDto);
            return ResponseEntity.ok(newPlan);
        } catch (PlanNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<PlanDto>> getAllPlans() {
        try {
            List<PlanDto> plans = planService.getAllPlans();
            plans.sort((p1, p2) -> p2.getPlanId().compareTo(p1.getPlanId()));
            return ResponseEntity.ok(plans);
        } catch (PlanNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDto> getPlanById(@PathVariable Integer id) {
        try {
            Optional<PlanDto> plan = planService.getPlanById(id);
            if (plan.isEmpty()) {
                throw new PlanNotFoundException("Plan with ID " + id + " not found");
            }
            return ResponseEntity.ok(plan.get());
        } catch (PlanNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDto> updatePlan(@PathVariable Integer id, @RequestBody PlanDto planDto) {
        try {
            PlanDto updatedPlan = planService.updatePlan(id, planDto);
            if (updatedPlan != null) {
                return ResponseEntity.ok(updatedPlan);
            } else {
                throw new PlanNotFoundException("Plan with ID " + id + " not found for update");
            }
        } catch (PlanNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
        try {
            Optional<PlanDto> plan = planService.getPlanById(id);
            if (plan.isPresent()) {
                planService.deletePlan(id);
                return ResponseEntity.noContent().build();
            } else {
                throw new PlanNotFoundException("Plan with ID " + id + " not found for deletion");
            }
        } catch (PlanNotFoundException e) {
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
