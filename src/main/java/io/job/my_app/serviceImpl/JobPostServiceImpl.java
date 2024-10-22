package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.JobPost;
import io.job.my_app.dto.JobPostingDto;
import io.job.my_app.exception.ResourceNotFoundException;
import io.job.my_app.repos.JobPostingRepo;
import io.job.my_app.service.JobPostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobPostServiceImpl implements JobPostService {

    private final JobPostingRepo jobPostingRepo;

    private final ModelMapper modelMapper;

    public JobPostServiceImpl(JobPostingRepo jobPostingRepo, ModelMapper modelMapper) {
        this.jobPostingRepo = jobPostingRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public JobPostingDto createJobPost(JobPostingDto jobPostingDto) {
        try {
            JobPost jobPost = modelMapper.map(jobPostingDto, JobPost.class);
            JobPost savedJobPosting = jobPostingRepo.save(jobPost);
            return modelMapper.map(savedJobPosting, JobPostingDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating job posting: " + e.getMessage());
        }
    }

    @Override
    public Optional<JobPostingDto> getJobPostById(Integer jobId) {
        try {
            JobPost jobPost = jobPostingRepo.findById(jobId)
                    .orElseThrow(() -> new ResourceNotFoundException("JobPosting not found with id: " + jobId));
            return Optional.of(modelMapper.map(jobPost, JobPostingDto.class));
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching job posting: " + e.getMessage());
        }
    }

    @Override
    public Page<JobPostingDto> getAllJobPosts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<JobPost> all = jobPostingRepo.findAll(pageable);

        List<JobPostingDto> jobPostingDtoList = all.getContent().stream()
                .map(jobPost -> modelMapper.map(jobPost, JobPostingDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(jobPostingDtoList, pageable, all.getTotalElements());
    }

    @Override
    public Optional<JobPostingDto> updateJobPost(Integer jobId, JobPostingDto jobPostingDto) {
        try {
            JobPost existingJobPost = jobPostingRepo.findById(jobId)
                    .orElseThrow(() -> new ResourceNotFoundException("JobPost not found with id: " + jobId));

            existingJobPost.setTitle(jobPostingDto.getTitle());
            existingJobPost.setDescription(jobPostingDto.getDescription());
            existingJobPost.setStatus(jobPostingDto.getStatus());
            existingJobPost.setPostedDate(jobPostingDto.getPostedDate());

            JobPost updatedJobPost = jobPostingRepo.save(existingJobPost);
            return Optional.of(modelMapper.map(updatedJobPost, JobPostingDto.class));
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error updating job posting: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteJobPost(Integer jobId) {
        try {
            JobPost jobPost = jobPostingRepo.findById(jobId)
                    .orElseThrow(() -> new ResourceNotFoundException("JobPosting not found with id: " + jobId));
            jobPostingRepo.delete(jobPost);
            return true;
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Error deleting job posting: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting job posting: " + e.getMessage());
        }
    }
}
