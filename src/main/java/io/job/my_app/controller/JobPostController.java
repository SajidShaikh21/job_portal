package io.job.my_app.controller;

import io.job.my_app.dto.JobPostingDto;
import io.job.my_app.exception.JobPostNotFoundException;
import io.job.my_app.service.JobPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobPostController
{
    @Autowired
    private JobPostService jobPostService;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/add")
    public ResponseEntity<?> createJobPost(@RequestBody JobPostingDto jobPostingDto) {
        try {
            JobPostingDto createdJobPost = jobPostService.createJobPost(jobPostingDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Job posting added successfully");
        } catch (JobPostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job posting not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create job posting: " + e.getMessage());
        }
    }

    @GetMapping("/sort")
    public ResponseEntity<?> getAllJobPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "jobId") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Page<JobPostingDto> allJobPosts = jobPostService.getAllJobPosts(page, size, sortBy, sortDir);

            if (allJobPosts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No job postings available at this time.");
            }
            return ResponseEntity.ok(allJobPosts);
        } catch (JobPostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Job postings not found.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all jobs: " + e.getMessage(), e);
        }
    }
    @GetMapping("/{jobId}")
    public ResponseEntity<?> getJobPostById(@PathVariable Integer jobId) {
        try {
            Optional<JobPostingDto> jobPostingDto = jobPostService.getJobPostById(jobId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Job posting added successfully");

        } catch (JobPostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job post not found with ID: " + jobId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve job posting: " + e.getMessage(), e);
        }
    }


    @PutMapping("/update/{jobId}")
    public ResponseEntity<?> updateJobPost(
            @PathVariable Integer jobId,
            @RequestBody JobPostingDto jobPostingDto) {
        try {
            Optional<JobPostingDto> updatedJobPost = jobPostService.updateJobPost(jobId, jobPostingDto);

            if (updatedJobPost != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Job post updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job posting not found");
            }
        } catch (JobPostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job posting not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update job posting: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<String> deleteJobPost(@PathVariable Integer jobId) {
        try {
            boolean isDeleted = jobPostService.deleteJobPost(jobId);
            if (isDeleted) {
                return ResponseEntity.ok("Job post deleted.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job post not found");
        } catch (JobPostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job post not found.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete job post with given ID: " + jobId, e);
        }
    }
}

