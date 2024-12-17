package io.job.my_app.Entity.ControllerTest;

import io.job.my_app.controller.UserDomainsController;
import io.job.my_app.dto.UserDomainsDto;
import io.job.my_app.service.UserDomainsService;
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
public class UserDomainsControllerTest {

    @Mock
    private UserDomainsService userDomainsService;

    @InjectMocks
    private UserDomainsController userDomainsController;

    private UserDomainsDto userDomainsDto;

    @BeforeEach
    public void setUp() {
        userDomainsDto = new UserDomainsDto();
        userDomainsDto.setUserDomainId(1);

    }

    @Test
    public void testCreateUserDomain_Success() {
        when(userDomainsService.createUserDomain(any(UserDomainsDto.class))).thenReturn(userDomainsDto);
        ResponseEntity<?> response = userDomainsController.createUserDomain(userDomainsDto);
        assert(response.getStatusCode() == HttpStatus.CREATED);
        assert(response.getBody().equals("UserDomain created successfully."));
    }

    @Test
    public void testCreateUserDomain_Failure() {
        when(userDomainsService.createUserDomain(any(UserDomainsDto.class))).thenThrow(new RuntimeException("Error creating UserDomain"));
        ResponseEntity<?> response = userDomainsController.createUserDomain(userDomainsDto);
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Failed to create UserDomain: Error creating UserDomain"));
    }

    @Test
    public void testGetAllUserDomains_Success() {
        List<UserDomainsDto> domains = Arrays.asList(userDomainsDto);
        when(userDomainsService.getAllUserDomains()).thenReturn(domains);
        ResponseEntity<?> response = userDomainsController.getAllUserDomains();
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(domains));
    }

    @Test
    public void testGetAllUserDomains_Failure() {
        when(userDomainsService.getAllUserDomains()).thenThrow(new RuntimeException("Error fetching UserDomains"));
        ResponseEntity<?> response = userDomainsController.getAllUserDomains();
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Failed to fetch UserDomains: Error fetching UserDomains"));
    }

    @Test
    public void testGetUserDomainById_Success() {
        when(userDomainsService.getUserDomainById(anyInt())).thenReturn(userDomainsDto);
        ResponseEntity<?> response = userDomainsController.getUserDomainById(1);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(userDomainsDto));
    }

    @Test
    public void testGetUserDomainById_Failure() {
        when(userDomainsService.getUserDomainById(anyInt())).thenThrow(new RuntimeException("Error retrieving UserDomain"));
        ResponseEntity<?> response = userDomainsController.getUserDomainById(1);
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Failed to retrieve UserDomain: Error retrieving UserDomain"));
    }

    @Test
    public void testUpdateUserDomain_Success() {
        when(userDomainsService.updateUserDomain(anyInt(), any(UserDomainsDto.class))).thenReturn(userDomainsDto);
        ResponseEntity<?> response = userDomainsController.updateUserDomain(1, userDomainsDto);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals("UserDomain updated successfully."));
    }

    @Test
    public void testUpdateUserDomain_Failure() {
        when(userDomainsService.updateUserDomain(anyInt(), any(UserDomainsDto.class))).thenThrow(new RuntimeException("Error updating UserDomain"));
        ResponseEntity<?> response = userDomainsController.updateUserDomain(1, userDomainsDto);
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Failed to update UserDomain: Error updating UserDomain"));
    }

    @Test
    public void testDeleteUserDomain_Success() {
        doNothing().when(userDomainsService).deleteUserDomain(anyInt());
        ResponseEntity<?> response = userDomainsController.deleteUserDomain(1);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals("UserDomain deleted successfully."));
    }

    @Test
    public void testDeleteUserDomain_Failure() {
        doThrow(new RuntimeException("Error deleting UserDomain")).when(userDomainsService).deleteUserDomain(anyInt());
        ResponseEntity<?> response = userDomainsController.deleteUserDomain(1);
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Failed to delete UserDomain: Error deleting UserDomain"));
    }
}


