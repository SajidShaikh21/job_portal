package io.job.my_app.Entity.ControllerTest;

import io.job.my_app.controller.UserSubDomainsController;
import io.job.my_app.dto.UserSubDomainsDto;
import io.job.my_app.service.UserSubDomainsService;
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

@ExtendWith(MockitoExtension.class)
public class UserSubDomainsControllerTest{

    @Mock
    private UserSubDomainsService userSubDomainsService;

    @InjectMocks
    private UserSubDomainsController userSubDomainsController;

    private UserSubDomainsDto userSubDomainsDto;

    @BeforeEach
    public void setUp() {
        userSubDomainsDto = new UserSubDomainsDto();
        userSubDomainsDto.setSubDomainId(1);

    }

    @Test
    public void testCreateUserSubDomain_Success() {
        when(userSubDomainsService.createUserSubDomain(any(UserSubDomainsDto.class))).thenReturn(userSubDomainsDto);
        ResponseEntity<?> response = userSubDomainsController.createUserSubDomain(userSubDomainsDto);
        assert(response.getStatusCode() == HttpStatus.CREATED);
        assert(response.getBody().equals("UserSubDomain created successfully"));
    }

    @Test
    public void testCreateUserSubDomain_Failure() {
        when(userSubDomainsService.createUserSubDomain(any(UserSubDomainsDto.class))).thenThrow(new RuntimeException("Error creating UserSubDomain"));
        ResponseEntity<?> response = userSubDomainsController.createUserSubDomain(userSubDomainsDto);
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Failed to create UserSubDomain: Error creating UserSubDomain"));
    }

    @Test
    public void testGetUserSubDomainById_Success() {
        when(userSubDomainsService.getUserSubDomainById(anyInt())).thenReturn(userSubDomainsDto);
        ResponseEntity<?> response = userSubDomainsController.getUserSubDomainById(1);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(userSubDomainsDto));
    }

    @Test
    public void testGetUserSubDomainById_Failure() {
        when(userSubDomainsService.getUserSubDomainById(anyInt())).thenThrow(new RuntimeException("UserSubDomain not found"));
        ResponseEntity<?> response = userSubDomainsController.getUserSubDomainById(1);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
        assert(response.getBody().equals("UserSubDomain not found: UserSubDomain not found"));
    }

    @Test
    public void testGetAllUserSubDomains_Success() {
        List<UserSubDomainsDto> subDomains = Arrays.asList(userSubDomainsDto);
        when(userSubDomainsService.getAllUserSubDomains()).thenReturn(subDomains);
        ResponseEntity<?> response = userSubDomainsController.getAllUserSubDomains();
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(subDomains));
    }

    @Test
    public void testGetAllUserSubDomains_Failure() {
        when(userSubDomainsService.getAllUserSubDomains()).thenThrow(new RuntimeException("Error fetching UserSubDomains"));
        ResponseEntity<?> response = userSubDomainsController.getAllUserSubDomains();
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Failed to fetch UserSubDomains: Error fetching UserSubDomains"));
    }

    @Test
    public void testUpdateUserSubDomain_Success() {
        when(userSubDomainsService.updateUserSubDomain(anyInt(), any(UserSubDomainsDto.class))).thenReturn(userSubDomainsDto);
        ResponseEntity<?> response = userSubDomainsController.updateUserSubDomain(1, userSubDomainsDto);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals("UserSubDomain updated successfully"));
    }

    @Test
    public void testUpdateUserSubDomain_Failure() {
        when(userSubDomainsService.updateUserSubDomain(anyInt(), any(UserSubDomainsDto.class))).thenThrow(new RuntimeException("Error updating UserSubDomain"));
        ResponseEntity<?> response = userSubDomainsController.updateUserSubDomain(1, userSubDomainsDto);
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Failed to update UserSubDomain: Error updating UserSubDomain"));
    }

    @Test
    public void testDeleteUserSubDomain_Success() {
        doNothing().when(userSubDomainsService).deleteUserSubDomain(anyInt());
        ResponseEntity<?> response = userSubDomainsController.deleteUserSubDomain(1);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals("UserSubDomain deleted successfully"));
    }

    @Test
    public void testDeleteUserSubDomain_Failure() {
        doThrow(new RuntimeException("Error deleting UserSubDomain")).when(userSubDomainsService).deleteUserSubDomain(anyInt());
        ResponseEntity<?> response = userSubDomainsController.deleteUserSubDomain(1);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
        assert(response.getBody().equals("Failed to delete UserSubDomain: Error deleting UserSubDomain"));
    }
}


