package io.job.my_app.controller;

import io.job.my_app.dto.SubDomainDto;
import io.job.my_app.exception.SubDomainNotFoundException;
import io.job.my_app.service.SubDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subdomains")
public class SubDomainController {

    @Autowired
    private SubDomainService subDomainService;

    @PostMapping("/add")
    public ResponseEntity<?> createSubDomain(@RequestBody SubDomainDto subDomainDto) {
        try {
            SubDomainDto createdSubDomain = subDomainService.createSubDomain(subDomainDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("SubDomain created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create subdomain: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSubDomains() {
        try {
            List<SubDomainDto> subDomains = subDomainService.getAllSubDomains();
            if (subDomains.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No subdomains found.");
            }
            return ResponseEntity.ok(subDomains);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch subdomains: " + e.getMessage());
        }
    }

    @GetMapping("/{subDomainId}")
    public ResponseEntity<?> getSubDomainById(@PathVariable Integer subDomainId) {
        try {
            SubDomainDto subDomain = subDomainService.getSubDomainById(subDomainId);
            return ResponseEntity.ok(subDomain);
        } catch (SubDomainNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve subdomain: " + e.getMessage());
        }
    }

    @PutMapping("/update/{subDomainId}")
    public ResponseEntity<?> updateSubDomain(@PathVariable Integer subDomainId, @RequestBody SubDomainDto subDomainDto) {
        try {
            SubDomainDto updatedSubDomain = subDomainService.updateSubDomain(subDomainId, subDomainDto);
            return ResponseEntity.ok("SubDomain updated successfully");
        } catch (SubDomainNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update subdomain: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{subDomainId}")
    public ResponseEntity<?> deleteSubDomain(@PathVariable Integer subDomainId) {
        try {
            subDomainService.deleteSubDomain(subDomainId);
            return ResponseEntity.ok("SubDomain deleted successfully");
        } catch (SubDomainNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete subdomain: " + e.getMessage());
        }
    }
}


