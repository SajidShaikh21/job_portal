package io.job.my_app.Entity.ControllerTest;

import io.job.my_app.controller.DomainController;
import io.job.my_app.dto.DomainDto;
import io.job.my_app.exception.DomainNotFoundException;
import io.job.my_app.service.DomainService;
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
public class DomainControllerTest {

    @Mock
    private DomainService domainService;

    @InjectMocks
    private DomainController domainController;

    private DomainDto domainDto;

    @BeforeEach
    public void setUp() {
        domainDto = new DomainDto();
        domainDto.setDomainId(1);
        domainDto.setDomainName("Test Domain");

    }

    @Test
    public void testCreateDomain_Success() {
        when(domainService.createDomain(any(DomainDto.class))).thenReturn(domainDto);
        ResponseEntity<?> response = domainController.createDomain(domainDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Domain created successfully", response.getBody());
    }

    @Test
    public void testCreateDomain_Failure() {
        when(domainService.createDomain(any(DomainDto.class))).thenThrow(new RuntimeException("Error creating domain"));
        ResponseEntity<?> response = domainController.createDomain(domainDto);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to create domain: Error creating domain", response.getBody());
    }

    @Test
    public void testGetAllDomains_Success() {
        List<DomainDto> domains = Arrays.asList(domainDto);
        when(domainService.getAllDomains()).thenReturn(domains);
        ResponseEntity<?> response = domainController.getAllDomains();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(domains, response.getBody());
    }

    @Test
    public void testGetAllDomains_Failure() {
        when(domainService.getAllDomains()).thenThrow(new RuntimeException("Error fetching domains"));
        ResponseEntity<?> response = domainController.getAllDomains();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to fetch domains: Error fetching domains", response.getBody());
    }

    @Test
    public void testGetDomainById_Success() {
        when(domainService.getDomainById(anyInt())).thenReturn(domainDto);
        ResponseEntity<?> response = domainController.getDomainById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(domainDto, response.getBody());
    }

    @Test
    public void testGetDomainById_NotFound() {
        when(domainService.getDomainById(anyInt())).thenThrow(new DomainNotFoundException("Domain not found"));
        ResponseEntity<?> response = domainController.getDomainById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Domain not found", response.getBody());
    }

    @Test
    public void testUpdateDomain_Success() {
        when(domainService.updateDomain(anyInt(), any(DomainDto.class))).thenReturn(domainDto);
        ResponseEntity<?> response = domainController.updateDomain(1, domainDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Domain updated successfully", response.getBody());
    }

    @Test
    public void testUpdateDomain_NotFound() {
        when(domainService.updateDomain(anyInt(), any(DomainDto.class))).thenThrow(new DomainNotFoundException("Domain not found"));
        ResponseEntity<?> response = domainController.updateDomain(1, domainDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Domain not found", response.getBody());
    }

    @Test
    public void testDeleteDomain_Success() {
        doNothing().when(domainService).deleteDomain(anyInt());
        ResponseEntity<?> response = domainController.deleteDomain(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Domain deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteDomain_NotFound() {
        doThrow(new DomainNotFoundException("Domain not found")).when(domainService).deleteDomain(anyInt());
        ResponseEntity<?> response = domainController.deleteDomain(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Domain not found", response.getBody());
    }
}
