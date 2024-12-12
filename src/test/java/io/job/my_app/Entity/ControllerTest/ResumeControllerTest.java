package io.job.my_app.Entity.ControllerTest;
import io.job.my_app.Entity.Resume;
import io.job.my_app.controller.ResumeController;
import io.job.my_app.service.ResumeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResumeControllerTest {

    @InjectMocks
    private ResumeController resumeController;

    @Mock
    private ResumeService resumeService;

    @Mock
    private MultipartFile file;

    private Resume resume;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resume = new Resume();
        resume.setId(1);
        resume.setId(1);

    }

    @Test
    void testUploadResume_Success() throws Exception {
        Integer userId = 1;
        String education = "B.E.";
        String experience = "4 years";
        String designation = "Software Engineer";
        String previousDesignation = "Java Developer";
        String skills = "Java, Spring Boot";
        when(resumeService.uploadResume(userId, file, education, experience, designation, previousDesignation, skills))
                .thenReturn(resume);
        ResponseEntity<Object> response = resumeController.uploadResume(userId, file, education, experience, designation, previousDesignation, skills);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Resume);
        assertEquals(resume, response.getBody());
        verify(resumeService, times(1)).uploadResume(userId, file, education, experience, designation, previousDesignation, skills);
    }

    @Test
    void testUploadResume_Failure() throws Exception {
        Integer userId = 1;
        String education = "Bachelor's Degree";
        String experience = "3 years";
        String designation = "Software Engineer";
        String previousDesignation = "Junior Developer";
        String skills = "Java, Spring Boot";
        when(resumeService.uploadResume(userId, file, education, experience, designation, previousDesignation, skills))
                .thenThrow(new RuntimeException("Error uploading resume"));
        ResponseEntity<Object> response = resumeController.uploadResume(userId, file, education, experience, designation, previousDesignation, skills);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody()); // Ensure the body is not null
        assertTrue(response.getBody().toString().contains("Failed to upload file: Error uploading resume"));
        verify(resumeService, times(1)).uploadResume(userId, file, education, experience, designation, previousDesignation, skills);
    }

    @Test
    void testDownloadResume_Success() throws Exception {
        Integer resumeId = 1;
        byte[] fileData = "dummy resume content".getBytes();
        ByteArrayResource fileResource = new ByteArrayResource(fileData);
        when(resumeService.getResumeFile(resumeId)).thenReturn(fileResource);
        ResponseEntity<String> response = resumeController.downloadResume(resumeId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("File saved successfully"));
        verify(resumeService, times(1)).getResumeFile(resumeId);
        Path folderPath = Paths.get("C:\\Users\\ajrra\\Downloads");
        assertTrue(Files.exists(folderPath));
        Path filePath = folderPath.resolve("resume_" + resumeId + ".pdf");
        assertTrue(Files.exists(filePath));
    }
    @Test
    void testDownloadResume_NotFound() throws Exception {
        Integer id = 123;
        when(resumeService.getResumeFile(id)).thenThrow(new IllegalArgumentException("Resume not found"));
        ResponseEntity<String> response = resumeController.downloadResume(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resume not found", response.getBody());
    }

    @Test
    void testDownloadResume_UnexpectedError() throws Exception {
        Integer id = 123;
        byte[] fileBytes = "test resume content".getBytes();
        ByteArrayResource byteArrayResource = new ByteArrayResource(fileBytes);
        when(resumeService.getResumeFile(id)).thenReturn(byteArrayResource);
        doThrow(new NullPointerException("Unexpected error")).when(resumeService).getResumeFile(id);
        ResponseEntity<String> response = resumeController.downloadResume(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("An error occurred"));
    }
    @Test
    void testUpdateResume_Success() throws Exception {
        Integer id = 1;
        MultipartFile file = new MockMultipartFile("file", "resume.pdf", "application/pdf", new byte[0]);
        String education = "Bachelor's Degree";
        String experience = "6 years in software development";
        String designation = "Software Engineer";
        String previousDesignation = "Senior Developer";
        String skills = "Java, Spring, MySQL";
        Resume mockUpdatedResume = new Resume();
        when(resumeService.updateResume(eq(id), eq(file), eq(education), eq(experience), eq(designation), eq(previousDesignation), eq(skills)))
                .thenReturn(mockUpdatedResume);
        ResponseEntity<Object> response = resumeController.updateResume(id, file, education, experience, designation, previousDesignation, skills);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockUpdatedResume, response.getBody());
    }
    @Test
    void testUpdateResume_Failure() throws Exception {
        Integer id = 1;
        MultipartFile file = null;
        String education = "Bachelor's Degree";
        String experience = "5 years in software development";
        String designation = "Software Engineer";
        String previousDesignation = "Junior Developer";
        String skills = "Java, Spring, MicroServices";
        when(resumeService.updateResume(eq(id), eq(file), eq(education), eq(experience), eq(designation), eq(previousDesignation), eq(skills)))
                .thenThrow(new IllegalArgumentException("Resume not found"));
        ResponseEntity<Object> response = resumeController.updateResume(id, file, education, experience, designation, previousDesignation, skills);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resume not found", response.getBody());
    }
    @Test
    void testDeleteResume_Success() {
        Integer id = 1;
        doNothing().when(resumeService).deleteResume(id);
        ResponseEntity<Object> response = resumeController.deleteResume(id);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteResume_Failure() {
        Integer id = 1;
        doThrow(new IllegalArgumentException("Resume not found")).when(resumeService).deleteResume(id);
        ResponseEntity<Object> response = resumeController.deleteResume(id);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Resume not found", response.getBody());
    }
}




