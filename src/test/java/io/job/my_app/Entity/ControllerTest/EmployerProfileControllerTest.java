package io.job.my_app.Entity.ControllerTest;
import io.job.my_app.controller.EmployerProfileController;
import io.job.my_app.dto.EmployerProfileDto;
import io.job.my_app.exception.EmployerProfileNotFoundException;
import io.job.my_app.service.EmployerProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployerProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployerProfileService service;

    @InjectMocks
    private EmployerProfileController controller;

    private EmployerProfileDto employerProfileDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        employerProfileDto = new EmployerProfileDto();
        employerProfileDto.setEmployerId(1);
        employerProfileDto.setEmployerName("Shreya");
        employerProfileDto.setIndustry("IT");
    }

    @Test
    void createEmployerProfile_Success() throws Exception {
        when(service.createEmployerProfile(any(EmployerProfileDto.class))).thenReturn(employerProfileDto);

        mockMvc.perform(post("/api/employer-profiles")
                        .contentType("application/json")
                        .content("{\"employerName\": \"Test Employer\", \"industry\": \"IT\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employerName").value("Shreya"))
                .andExpect(jsonPath("$.industry").value("IT"));
    }

    @Test
    void createEmployerProfile_Failure() throws Exception {
        when(service.createEmployerProfile(any(EmployerProfileDto.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(post("/api/employer-profiles")
                        .contentType("application/json")
                        .content("{\"employerName\": \"Shreya\", \"industry\": \"IT\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(" Employer profile Not Found."));
    }

    @Test
    void getEmployerProfileById_Success() throws Exception {
        when(service.getEmployerProfileById(1)).thenReturn(employerProfileDto);

        mockMvc.perform(get("/api/employer-profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employerName").value("Shreya"))
                .andExpect(jsonPath("$.industry").value("IT"));
    }

    @Test
    void getEmployerProfileById_NotFound() throws Exception {
        when(service.getEmployerProfileById(1)).thenThrow(new EmployerProfileNotFoundException("Profile not found"));

        mockMvc.perform(get("/api/employer-profiles/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employer profile with ID 1 not found."));
    }


    @Test
    void updateEmployerProfile_Success() throws Exception {

        EmployerProfileDto updatedProfile = new EmployerProfileDto();
        updatedProfile.setEmployerName("Rutuja");
        updatedProfile.setIndustry("IT");
        when(service.updateEmployerProfile(eq(1), any(EmployerProfileDto.class)))
                .thenReturn(updatedProfile);

        mockMvc.perform(put("/api/employer-profiles/1")
                        .contentType("application/json")
                        .content("{\"employerName\": \"Rutuja\", \"industry\": \"IT\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"employerName\": \"Rutuja\", \"industry\": \"IT\"}"));
    }

    @Test
    void updateEmployerProfile_NotFound() throws Exception {
        when(service.updateEmployerProfile(eq(1), any(EmployerProfileDto.class)))
                .thenThrow(new EmployerProfileNotFoundException("Profile not found"));
        mockMvc.perform(put("/api/employer-profiles/1")
                        .contentType("application/json")
                        .content("{\"employerName\": \"Updated Employer\", \"industry\": \"IT\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employer profile with ID 1 not found."));
    }

    @Test
    void deleteEmployerProfile_Success() throws Exception {
        doNothing().when(service).deleteEmployerProfile(1);
        when(service.getEmployerProfileById(1)).thenReturn(employerProfileDto);
        mockMvc.perform(delete("/api/employer-profiles/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employer Profile Deleted Successfully"));
    }

    @Test
    void deleteEmployerProfile_NotFound() throws Exception {
        when(service.getEmployerProfileById(1)).thenThrow(new EmployerProfileNotFoundException("Profile not found"));
        mockMvc.perform(delete("/api/employer-profiles/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employer profile with ID 1 not found."));
    }


}