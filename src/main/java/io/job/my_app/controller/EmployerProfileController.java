package io.job.my_app.controller;

import io.job.my_app.dto.EmployerProfileDto;
import io.job.my_app.exception.EmployerProfileNotFoundException;
import io.job.my_app.service.EmployerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer-profiles")
public class EmployerProfileController {

    @Autowired
    private EmployerProfileService service;

    @PostMapping
    public ResponseEntity<?> createEmployerProfile(@RequestBody EmployerProfileDto dto) {
        try {
            EmployerProfileDto createdProfile = service.createEmployerProfile(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(" Employer profile Not Found.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployerProfileById(@PathVariable Integer id) {
        try {
            EmployerProfileDto profile = service.getEmployerProfileById(id);
            return ResponseEntity.ok(profile);
        } catch (EmployerProfileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employer profile with ID " + id + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the employer profile.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployerProfile(
            @PathVariable Integer id, @RequestBody EmployerProfileDto employerProfileDto) {
        try {
            EmployerProfileDto updatedProfile = service.updateEmployerProfile(id, employerProfileDto);
            return ResponseEntity.ok(updatedProfile);
        } catch (EmployerProfileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employer profile with ID " + id + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the employer profile.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployerProfile(@PathVariable Integer id) {
        try {
            service.getEmployerProfileById(id); // Validate the profile exists
            service.deleteEmployerProfile(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Employer Profile Deleted Successfully");
        } catch (EmployerProfileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employer profile with ID " + id + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the employer profile.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployerProfiles(
            @PageableDefault(size = 10, sort = "employerName") Pageable pageable) {
        try {
            Page<EmployerProfileDto> profiles = service.getAllEmployerProfiles(pageable);
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving employer profiles.");
        }
    }
}
