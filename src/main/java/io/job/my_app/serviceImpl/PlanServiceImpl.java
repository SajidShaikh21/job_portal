package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.Plan;
import io.job.my_app.dto.PlanDto;
import io.job.my_app.repos.PlanRepository;
import io.job.my_app.repos.UserRepo;
import io.job.my_app.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepo userRepo;

    // Convert DTO to Entity
    private Plan convertToEntity(PlanDto planDto) {
        Plan plan = new Plan();
        plan.setPlanName(planDto.getPlanName());
        plan.setMonthlyPostLimit(planDto.getMonthlyPostLimit());
        plan.setYearlyPostLimit(planDto.getYearlyPostLimit());
        plan.setPrice(planDto.getPrice());


        return plan;
    }

    // Convert Entity to DTO
    private PlanDto convertToDto(Plan plan) {
        PlanDto planDto = new PlanDto();
        planDto.setPlanId(plan.getPlanId());
        planDto.setPlanName(plan.getPlanName());
        planDto.setMonthlyPostLimit(plan.getMonthlyPostLimit());
        planDto.setYearlyPostLimit(plan.getYearlyPostLimit());
        planDto.setPrice(plan.getPrice());

        return planDto;
    }

    // Create a new Plan
    @Override
    public PlanDto createPlan(PlanDto planDto) {
        Plan plan = convertToEntity(planDto);
        Plan savedPlan = planRepository.save(plan);
        return convertToDto(savedPlan);
    }

    // Get all Plans
    @Override
    public List<PlanDto> getAllPlans() {
        return planRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get a Plan by ID
    @Override
    public Optional<PlanDto> getPlanById(Integer id) {
        return planRepository.findById(id)
                .map(this::convertToDto);
    }

    // Update a Plan
    @Override
    public PlanDto updatePlan(Integer id, PlanDto planDto) {
        Optional<Plan> existingPlan = planRepository.findById(id);
        if (existingPlan.isPresent()) {
            Plan plan = existingPlan.get();
            plan.setPlanName(planDto.getPlanName());
            plan.setMonthlyPostLimit(planDto.getMonthlyPostLimit());
            plan.setYearlyPostLimit(planDto.getYearlyPostLimit());
            plan.setPrice(planDto.getPrice());


            Plan updatedPlan = planRepository.save(plan);
            return convertToDto(updatedPlan);
        } else {
            return null;
        }
    }

    // Delete a Plan by ID
    @Override
    public void deletePlan(Integer id) {
        planRepository.deleteById(id);
    }
}
