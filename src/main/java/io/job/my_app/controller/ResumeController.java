package io.job.my_app.controller;

import io.job.my_app.Entity.Resume;
import io.job.my_app.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadResume(
            @RequestParam Integer userId,
            @RequestParam MultipartFile file,
            @RequestParam String education,
            @RequestParam String experience,
            @RequestParam String designation,
            @RequestParam String previousDesignation,
            @RequestParam String skills) {
        try {
            Resume resume = resumeService.uploadResume(userId, file, education, experience, designation, previousDesignation, skills);
            return new ResponseEntity<>(resume, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadResume(@PathVariable Integer id) {
        try {

            ByteArrayResource file = resumeService.getResumeFile(id);

            String downloadFolder ="C:\\Users\\ajrra\\Downloads";
            Path folderPath = Paths.get(downloadFolder);

            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            String fileName = "resume_" + id + ".pdf";
            Path filePath = folderPath.resolve(fileName);


            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(file.getByteArray());
            }
            return ResponseEntity.ok("File saved successfully to " + filePath.toString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resume not found");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save the file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateResume(
            @PathVariable Integer id,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String education,
            @RequestParam String experience,
            @RequestParam String designation,
            @RequestParam String previousDesignation,
            @RequestParam String skills) {
        try {
            Resume updatedResume = resumeService.updateResume(id, file, education, experience, designation, previousDesignation, skills);
            return new ResponseEntity<>(updatedResume, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update resume: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteResume(@PathVariable Integer id) {
        try {
            resumeService.deleteResume(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete resume with ID: " + id);
        }
    }
}
