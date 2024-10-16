package io.job.my_app.service;

import io.job.my_app.dto.PlanDto;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    // Create a new Plan
    PlanDto createPlan(PlanDto planDto);

    // Get all Plans
    List<PlanDto> getAllPlans();

    // Get a Plan by ID
    Optional<PlanDto> getPlanById(Integer id);

    // Update a Plan by ID
    PlanDto updatePlan(Integer id, PlanDto planDto);

    // Delete a Plan by ID
    void deletePlan(Integer id);
}



