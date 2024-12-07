
package io.job.my_app.controller;


import io.job.my_app.dto.UserSkillsDto;
import io.job.my_app.service.UserSkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-skills")
public class UserSkillsController {

    @Autowired
    private UserSkillsService userSkillsService;

    @PostMapping
    public ResponseEntity<String> createUserSkill(@RequestBody UserSkillsDto userSkillsDTO) {
        try {
            UserSkillsDto createdSkill = userSkillsService.createUserSkill(userSkillsDTO);
            return ResponseEntity.ok("User skill created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserSkillById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(userSkillsService.getUserSkillById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user skill: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUserSkills() {
        try {
            List<UserSkillsDto> skills = userSkillsService.getAllUserSkills();
            return ResponseEntity.ok(skills);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user skills: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserSkill(@PathVariable Integer id, @RequestBody UserSkillsDto userSkillsDTO) {
        try {
            return ResponseEntity.ok(userSkillsService.updateUserSkill(id, userSkillsDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user skill: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserSkill(@PathVariable Integer id) {
        try {
            userSkillsService.deleteUserSkill(id);
            return ResponseEntity.ok("User skill deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user skill: " + e.getMessage());
        }
    }
}
