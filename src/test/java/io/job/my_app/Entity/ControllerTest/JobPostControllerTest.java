package io.job.my_app.Entity.ControllerTest;
import io.job.my_app.controller.JobPostController;
import io.job.my_app.dto.JobPostingDto;
import io.job.my_app.exception.JobPostNotFoundException;
import io.job.my_app.service.JobPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JobPostControllerTest {

    @Mock
    private JobPostService jobPostService;

    @InjectMocks
    private JobPostController jobPostController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
    }

    @Test
    void testCreateJobPost_Success() throws Exception {
        JobPostingDto jobPostingDto = new JobPostingDto();
        jobPostingDto.setTitle("Software Developer");
        jobPostingDto.setDescription("Description of the job");
        when(jobPostService.createJobPost(any(JobPostingDto.class))).thenReturn(jobPostingDto);
        mockMvc.perform(post("/api/jobs/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"jobTitle\": \"Software Developer\", \"jobDescription\": \"Description of the job\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Job posting added successfully"));
        verify(jobPostService, times(1)).createJobPost(any(JobPostingDto.class));
    }

    @Test
    void testCreateJobPost_NotFound() throws Exception {
        // Mock the service layer to throw JobPostNotFoundException
        when(jobPostService.createJobPost(any(JobPostingDto.class)))
                .thenThrow(new JobPostNotFoundException("Job posting not found"));

        // Perform POST request to /api/jobs/add
        mockMvc.perform(post("/api/jobs/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"jobTitle\": \"Software Developer\", \"jobDescription\": \"Description of the job\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Job posting not found"));

        // Verify interaction with the service layer
        verify(jobPostService, times(1)).createJobPost(any(JobPostingDto.class));
    }


    @Test
    void testGetJobPostById_Success() throws Exception {
        Integer jobId = 1;
        JobPostingDto jobPostingDto = new JobPostingDto();
        jobPostingDto.setTitle("Software Developer");
        jobPostingDto.setDescription("Description of the software developer job");
        when(jobPostService.getJobPostById(jobId)).thenReturn(Optional.of(jobPostingDto));
        mockMvc.perform(get("/api/jobs/{jobId}", jobId))
                .andExpect(status().isCreated())
                .andExpect(content().string("Job posting added successfully"));
        verify(jobPostService, times(1)).getJobPostById(jobId);
    }
    @Test
    void testGetJobPostById_JobPostNotFound() {
        Integer jobId = 123;
        when(jobPostService.getJobPostById(jobId)).thenThrow(new JobPostNotFoundException("Job post not found"));
        ResponseEntity<?> response = jobPostController.getJobPostById(jobId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Job post not found with ID: " + jobId, response.getBody());
    }

    @Test
    void testUpdateJobPost_Success() throws Exception {
        Integer jobId = 1;
        JobPostingDto jobPostingDto = new JobPostingDto();
        jobPostingDto.setTitle("Updated Software Developer");
        jobPostingDto.setDescription("Updated job description");
        when(jobPostService.updateJobPost(eq(jobId), any(JobPostingDto.class)))
                .thenReturn(Optional.of(jobPostingDto));
        mockMvc.perform(put("/api/jobs/update/{jobId}", jobId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Software Developer\", \"description\": \"Updated job description\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Job post updated successfully"));
        verify(jobPostService, times(1)).updateJobPost(eq(jobId), any(JobPostingDto.class));
    }



    @Test
    void testGetAllJobPosts_success() {
        JobPostingDto jobPostingDto = new JobPostingDto();
        jobPostingDto.setJobId(Integer.valueOf("12345"));
        jobPostingDto.setTitle("Java Developer");
        jobPostingDto.setDescription("SpringBoot,Microservices");
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<JobPostingDto> jobPostingPage = new PageImpl<>(Collections.singletonList(jobPostingDto), pageRequest, 1);
        when(jobPostService.getAllJobPosts(0, 10, "jobId", "desc")).thenReturn(jobPostingPage);
        ResponseEntity<?> response = jobPostController.getAllJobPosts(0, 10, "jobId", "desc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jobPostingPage, response.getBody());
    }
    @Test
    void testGetAllJobPosts_UnSuccess() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<JobPostingDto> emptyPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);
        when(jobPostService.getAllJobPosts(0, 10, "jobId", "desc")).thenReturn(emptyPage);
        mockMvc.perform(get("/api/jobs/sort?page=0&size=10&sortBy=jobId&sortDir=desc"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("No job postings available at this time."));
        verify(jobPostService, times(1)).getAllJobPosts(0, 10, "jobId", "desc");
    }

    @Test
    void testDeleteJobPost_Success() throws Exception {
        Integer jobId = 1;
        when(jobPostService.deleteJobPost(jobId)).thenReturn(true);
        mockMvc.perform(delete("/api/jobs/delete/{jobId}", jobId))
                .andExpect(status().isOk())
                .andExpect(content().string("Job post deleted"));
        verify(jobPostService, times(1)).deleteJobPost(jobId);
    }
    @Test
    void testDeleteJobPost_NotFound() throws Exception {
        Integer jobId = 1;
        when(jobPostService.deleteJobPost(jobId)).thenReturn(false);
        mockMvc.perform(delete("/api/jobs/delete/{jobId}", jobId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Job post not found"));
        verify(jobPostService, times(1)).deleteJobPost(jobId);
    }

    @Test
    void testDeleteJobPost_JobPostNotFoundException() throws Exception {
        Integer jobId = 1;
        when(jobPostService.deleteJobPost(jobId)).thenThrow(new JobPostNotFoundException("Job post not found"));
        mockMvc.perform(delete("/api/jobs/delete/{jobId}", jobId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Job post not found"));
        verify(jobPostService, times(1)).deleteJobPost(jobId);
    }


}