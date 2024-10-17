package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.JobPost;
import io.job.my_app.dto.JobPostingDto;
import io.job.my_app.exception.ResourceNotFoundException;
import io.job.my_app.repos.JobPostingRepo;
import io.job.my_app.service.JobPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPostServiceImpl implements JobPostService {
    @Autowired
    private JobPostingRepo jobPostingRepo;
    @Autowired
    private ModelMapper modelMapper;

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
    public JobPostingDto getJobPostById(Integer jobId) {
        try {
            JobPost jobPost = jobPostingRepo.findById(Integer.valueOf(jobId))
                    .orElseThrow(() -> new ResourceNotFoundException("JobPosting not found with id: " + jobId));
            return modelMapper.map(jobPost,JobPostingDto.class);

        } catch (Exception | ResourceNotFoundException e) {
            throw new RuntimeException("Error fetching job posting: " + e.getMessage());
        }
    }

    @Override
    public List<JobPostingDto> getAllJobPosts() {
        try {
            List<JobPost> jobPosts = jobPostingRepo.findAll();
            return jobPosts.stream()
                    .map(jobPost -> modelMapper.map(jobPost, JobPostingDto.class))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all job postings: " + e.getMessage());
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
    public JobPostingDto updateJobPost(Integer jobId, JobPostingDto jobPostingDto) throws ResourceNotFoundException {
        JobPost existingJobPost = null;
        try {
            existingJobPost = jobPostingRepo.findById(jobId)
                    .orElseThrow(() -> new ResourceNotFoundException("JobPost not found with id: " + jobId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        existingJobPost.setTitle(jobPostingDto.getTitle());
        existingJobPost.setDescription(jobPostingDto.getDescription());
        existingJobPost.setStatus(jobPostingDto.getStatus());
        existingJobPost.setPostedDate(jobPostingDto.getPostedDate());

        JobPost updatedJobPost = jobPostingRepo.save(existingJobPost);
        return modelMapper.map(updatedJobPost, JobPostingDto.class);
    }



    @Override
    public boolean deleteJobPost(Integer jobId) {
        try {
            JobPost jobPost = jobPostingRepo.findById(Integer.valueOf(jobId))
                    .orElseThrow(() -> new ResourceNotFoundException("JobPosting not found with id: " + jobId));
            jobPostingRepo.delete(jobPost);

        } catch (Exception | ResourceNotFoundException e) {
            throw new RuntimeException("Error deleting job posting: " + e.getMessage());
        }
        return false;
    }


}
