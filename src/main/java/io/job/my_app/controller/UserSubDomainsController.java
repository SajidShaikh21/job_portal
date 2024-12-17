package io.job.my_app.controller;

import io.job.my_app.dto.UserSubDomainsDto;
import io.job.my_app.service.UserSubDomainsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userSubDomains")
public class UserSubDomainsController {

    @Autowired
    private UserSubDomainsService service;

    @PostMapping("/add")
    public ResponseEntity<?> createUserSubDomain(@RequestBody UserSubDomainsDto dto) {
        try {
            UserSubDomainsDto created = service.createUserSubDomain(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("UserSubDomain created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create UserSubDomain: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserSubDomainById(@PathVariable Integer id) {
        try {
            UserSubDomainsDto dto = service.getUserSubDomainById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UserSubDomain not found: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUserSubDomains() {
        try {
            List<UserSubDomainsDto> list = service.getAllUserSubDomains();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch UserSubDomains: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserSubDomain(@PathVariable Integer id, @RequestBody UserSubDomainsDto dto) {
        try {
            UserSubDomainsDto updated = service.updateUserSubDomain(id, dto);
            return ResponseEntity.ok("UserSubDomain updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update UserSubDomain: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserSubDomain(@PathVariable Integer id) {
        try {
            service.deleteUserSubDomain(id);
            return ResponseEntity.ok("UserSubDomain deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete UserSubDomain: " + e.getMessage());
        }
    }
}



