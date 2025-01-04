package io.job.my_app.controller;

import io.job.my_app.dto.UserDomainsDto;
import io.job.my_app.service.UserDomainsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userdomains")
public class UserDomainsController {

    @Autowired
    private UserDomainsService userDomainsService;

    @PostMapping("/add")
    public ResponseEntity<?> createUserDomain(@RequestBody UserDomainsDto userDomainsDto) {
        try {
            UserDomainsDto createdUserDomain = userDomainsService.createUserDomain(userDomainsDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("UserDomain created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create UserDomain: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUserDomains() {
        try {
            List<UserDomainsDto> userDomainsList = userDomainsService.getAllUserDomains();
            if (userDomainsList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No UserDomains found.");
            }
            return ResponseEntity.ok(userDomainsList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch UserDomains: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDomainById(@PathVariable Integer id) {
        try {
            UserDomainsDto userDomainsDto = userDomainsService.getUserDomainById(id);
            return ResponseEntity.ok(userDomainsDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve UserDomain: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserDomain(@PathVariable Integer id, @RequestBody UserDomainsDto userDomainsDto) {
        try {
            UserDomainsDto updatedUserDomain = userDomainsService.updateUserDomain(id, userDomainsDto);
            return ResponseEntity.ok("UserDomain updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update UserDomain: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserDomain(@PathVariable Integer id) {
        try {
            userDomainsService.deleteUserDomain(id);
            return ResponseEntity.ok("UserDomain deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete UserDomain: " + e.getMessage());
        }
    }
}


