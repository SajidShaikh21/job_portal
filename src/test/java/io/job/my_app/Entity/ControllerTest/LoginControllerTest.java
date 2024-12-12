package io.job.my_app.Entity.ControllerTest;
import io.job.my_app.Entity.User;
import io.job.my_app.controller.LoginController;
import io.job.my_app.payloads.JwtAuthRequest;
import io.job.my_app.payloads.JwtAuthResponse;
import io.job.my_app.security.CustomUserDetailsService;
import io.job.my_app.security.JwtTokenHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private JwtTokenHelper jwtTokenHelper;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    private JwtAuthRequest jwtAuthRequest;
    private User mockUser;
    private UserDetails mockUserDetails;

    @BeforeEach
    public void setUp() {
        jwtAuthRequest = new JwtAuthRequest("ABC", "Abc123");
        mockUser = new User();
        mockUserDetails = mockUser;
    }

    @Test
    public void testGenerateToken_Success() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(customUserDetailsService.loadUserByUsername(anyString()))
                .thenReturn(mockUserDetails);
        when(jwtTokenHelper.generateToken(mockUserDetails, mockUser))
                .thenReturn("generatedToken");
        ResponseEntity<JwtAuthResponse> response = loginController.generateToken(jwtAuthRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("generatedToken", response.getBody().getToken());
        assertEquals("success", response.getBody().getMessage());
    }


    @Test
    public void testGenerateToken_UserNotFound() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new UsernameNotFoundException("User not found"));
        ResponseEntity<JwtAuthResponse> response = loginController.generateToken(jwtAuthRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("null", response.getBody().getToken());
        assertEquals("Invalid credentials", response.getBody().getMessage());
    }
}
