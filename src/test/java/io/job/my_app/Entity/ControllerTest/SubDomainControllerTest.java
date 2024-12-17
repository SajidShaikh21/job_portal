package io.job.my_app.Entity.ControllerTest;

import io.job.my_app.controller.SubDomainController;
import io.job.my_app.dto.SubDomainDto;
import io.job.my_app.exception.SubDomainNotFoundException;
import io.job.my_app.service.SubDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SubDomainControllerTest {

    @Mock
    private SubDomainService subDomainService;

    @InjectMocks
    private SubDomainController subDomainController;

    private SubDomainDto subDomainDto;

    @BeforeEach
    public void setUp() {
        subDomainDto = new SubDomainDto();
        subDomainDto.setSubDomainId(1);
        subDomainDto.setSubDomainName("SubDomain1");

    }

    @Test
    public void testCreateSubDomain_Success() {
        when(subDomainService.createSubDomain(any(SubDomainDto.class))).thenReturn(subDomainDto);
        ResponseEntity<?> response = subDomainController.createSubDomain(subDomainDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("SubDomain created successfully", response.getBody());
    }

    @Test
    public void testCreateSubDomain_Failure() {
        when(subDomainService.createSubDomain(any(SubDomainDto.class))).thenThrow(new RuntimeException("Error creating subdomain"));
        ResponseEntity<?> response = subDomainController.createSubDomain(subDomainDto);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to create subdomain: Error creating subdomain", response.getBody());
    }

    @Test
    public void testGetAllSubDomains_Success() {
        List<SubDomainDto> subDomains = Arrays.asList(subDomainDto);
        when(subDomainService.getAllSubDomains()).thenReturn(subDomains);
        ResponseEntity<?> response = subDomainController.getAllSubDomains();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subDomains, response.getBody());
    }

    @Test
    public void testGetAllSubDomains_Failure() {
        when(subDomainService.getAllSubDomains()).thenThrow(new RuntimeException("Error fetching subdomains"));
        ResponseEntity<?> response = subDomainController.getAllSubDomains();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to fetch subdomains: Error fetching subdomains", response.getBody());
    }

    @Test
    public void testGetSubDomainById_Success() {
        when(subDomainService.getSubDomainById(anyInt())).thenReturn(subDomainDto);
        ResponseEntity<?> response = subDomainController.getSubDomainById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subDomainDto, response.getBody());
    }

    @Test
    public void testGetSubDomainById_NotFound() {
        when(subDomainService.getSubDomainById(anyInt())).thenThrow(new SubDomainNotFoundException("Subdomain not found"));
        ResponseEntity<?> response = subDomainController.getSubDomainById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Subdomain not found", response.getBody());
    }

    @Test
    public void testUpdateSubDomain_Success() {
        when(subDomainService.updateSubDomain(anyInt(), any(SubDomainDto.class))).thenReturn(subDomainDto);
        ResponseEntity<?> response = subDomainController.updateSubDomain(1, subDomainDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SubDomain updated successfully", response.getBody());
    }

    @Test
    public void testUpdateSubDomain_NotFound() {
        when(subDomainService.updateSubDomain(anyInt(), any(SubDomainDto.class))).thenThrow(new SubDomainNotFoundException("Subdomain not found"));
        ResponseEntity<?> response = subDomainController.updateSubDomain(1, subDomainDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Subdomain not found", response.getBody());
    }

    @Test
    public void testDeleteSubDomain_Success() {
        doNothing().when(subDomainService).deleteSubDomain(anyInt());
        ResponseEntity<?> response = subDomainController.deleteSubDomain(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SubDomain deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteSubDomain_NotFound() {
        doThrow(new SubDomainNotFoundException("Subdomain not found")).when(subDomainService).deleteSubDomain(anyInt());
        ResponseEntity<?> response = subDomainController.deleteSubDomain(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Subdomain not found", response.getBody());
    }
}


