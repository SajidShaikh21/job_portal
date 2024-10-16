package io.job.my_app.service;

import io.job.my_app.dto.JobPostingDto;
import io.job.my_app.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface JobPostService {
    JobPostingDto createJobPost(JobPostingDto jobPostingDto);
    JobPostingDto getJobPostById(Integer jobId);
    List<JobPostingDto> getAllJobPosts();

    Page<JobPostingDto> getAllJobPosts(int page, int size, String sortBy, String sortDir);
    JobPostingDto updateJobPost(Integer jobId, JobPostingDto jobPostingDto) throws ResourceNotFoundException;
    boolean deleteJobPost(Integer jobId);

}
