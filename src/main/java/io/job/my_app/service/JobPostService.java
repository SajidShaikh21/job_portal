package io.job.my_app.service;

import io.job.my_app.dto.JobPostingDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface JobPostService {
    JobPostingDto createJobPost(JobPostingDto jobPostingDto);
    Optional<JobPostingDto> getJobPostById(Integer jobId);
    Page<JobPostingDto> getAllJobPosts(int page, int size, String sortBy, String sortDir);
    Optional<JobPostingDto> updateJobPost(Integer jobId, JobPostingDto jobPostingDto);
    boolean deleteJobPost(Integer jobId);
}