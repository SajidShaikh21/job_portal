package io.job.my_app.Entity.ControllerTest;
import io.job.my_app.controller.PlanController;
import io.job.my_app.dto.PlanDto;
import io.job.my_app.exception.PlanNotFoundException;
import io.job.my_app.service.PlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanControllerTest {

    @Mock
    private PlanService planService;

    @InjectMocks
    private PlanController planController;

    private PlanDto planDto;

    @BeforeEach
    void setUp() {
        planDto = new PlanDto();
        planDto.setPlanId(1);
        planDto.setPlanName("Test Plan");
    }
    @Test
    void createPlan_ShouldReturnCreatedPlan() {
        when(planService.createPlan(any(PlanDto.class))).thenReturn(planDto);
        ResponseEntity<PlanDto> response = planController.createPlan(planDto);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Plan", response.getBody().getPlanName());
    }
    @Test
    void createPlan_ShouldReturnNotFound_WhenPlanNotFound() {
        when(planService.createPlan(any(PlanDto.class))).thenThrow(PlanNotFoundException.class);
        ResponseEntity<PlanDto> response = planController.createPlan(planDto);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getAllPlans_ShouldReturnAllPlans() {
        when(planService.getAllPlans()).thenReturn(Collections.singletonList(planDto));
        ResponseEntity<List<PlanDto>> response = planController.getAllPlans();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
    @Test
    void getAllPlans_UnSuccess() {
        when(planService.getAllPlans()).thenThrow(PlanNotFoundException.class);
        ResponseEntity<List<PlanDto>> response = planController.getAllPlans();
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getPlanById_ShouldReturnPlan_WhenPlanExists() {
        when(planService.getPlanById(1)).thenReturn(Optional.of(planDto));
        ResponseEntity<PlanDto> response = planController.getPlanById(1);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getPlanId());
    }
    @Test
    void getPlanById_UnSuccess() {
        when(planService.getPlanById(1)).thenReturn(Optional.empty());

        ResponseEntity<PlanDto> response = planController.getPlanById(1);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void updatePlan_ShouldReturnUpdatedPlan_WhenPlanExists() {
        when(planService.updatePlan(1, planDto)).thenReturn(planDto);

        ResponseEntity<PlanDto> response = planController.updatePlan(1, planDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Plan", response.getBody().getPlanName());
    }
    @Test
    void updatePlan_UnSuccess() {
        when(planService.updatePlan(1, planDto)).thenReturn(null);
        ResponseEntity<PlanDto> response = planController.updatePlan(1, planDto);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
    @Test
    void deletePlan_ShouldReturnNoContent_WhenPlanExists() {
        when(planService.getPlanById(1)).thenReturn(Optional.of(planDto));
        ResponseEntity<Void> response = planController.deletePlan(1);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deletePlan_ShouldReturnNotFound_WhenPlanDoesNotExist() {
        when(planService.getPlanById(1)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = planController.deletePlan(1);
        assertEquals(404, response.getStatusCodeValue());
    }


}
