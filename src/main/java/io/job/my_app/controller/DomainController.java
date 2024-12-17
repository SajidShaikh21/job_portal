package io.job.my_app.controller;

import io.job.my_app.dto.DomainDto;
import io.job.my_app.exception.DomainNotFoundException;
import io.job.my_app.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @PostMapping("/add")
    public ResponseEntity<?> createDomain(@RequestBody DomainDto domainDto) {
        try {
            DomainDto createdDomain = domainService.createDomain(domainDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Domain created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create domain: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllDomains() {
        try {
            List<DomainDto> domains = domainService.getAllDomains();
            if (domains.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No domains found.");
            }
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch domains: " + e.getMessage());
        }
    }

    @GetMapping("/{domainId}")
    public ResponseEntity<?> getDomainById(@PathVariable Integer domainId) {
        try {
            DomainDto domain = domainService.getDomainById(domainId);
            return ResponseEntity.ok(domain);
        } catch (DomainNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve domain: " + e.getMessage());
        }
    }

    @PutMapping("/update/{domainId}")
    public ResponseEntity<?> updateDomain(@PathVariable Integer domainId, @RequestBody DomainDto domainDto) {
        try {
            DomainDto updatedDomain = domainService.updateDomain(domainId, domainDto);
            return ResponseEntity.ok("Domain updated successfully");
        } catch (DomainNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update domain: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{domainId}")
    public ResponseEntity<?> deleteDomain(@PathVariable Integer domainId) {
        try {

            domainService.deleteDomain(domainId);
            return ResponseEntity.ok("Domain deleted successfully");
        } catch (DomainNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete domain: " + e.getMessage());
        }
    }
}


