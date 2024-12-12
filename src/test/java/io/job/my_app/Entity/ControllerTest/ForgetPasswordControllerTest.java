package io.job.my_app.Entity.ControllerTest;

import io.job.my_app.Entity.Security.EmailVerification;
import io.job.my_app.Entity.User;
import io.job.my_app.controller.ForgetPasswordController;
import io.job.my_app.exception.InvalidOtpException;
import io.job.my_app.exception.OtpExpiredException;
import io.job.my_app.payloads.JwtAuthResponse;
import io.job.my_app.repos.EmailVerificationRepository;
import io.job.my_app.repos.UserRepo;
import io.job.my_app.security.CustomUserDetailsService;
import io.job.my_app.security.JwtTokenHelper;
import io.job.my_app.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ForgetPasswordControllerTest {

    @InjectMocks
    private ForgetPasswordController forgetPasswordController;

    @Mock
    private JwtTokenHelper jwtTokenHelper;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailService emailService;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;
    private PasswordEncoder encoder;
    private User user;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
    }
    @Test
    public void testGenerateToken_Success() throws Exception {
        String email = "abc123@example.com";
        String expectedToken = "validToken";
        UserDetails userDetails = mock(UserDetails.class);
        when(customUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(userRepo.findByEmail(email)).thenReturn(user);
        when(jwtTokenHelper.generateToken(userDetails, user)).thenReturn(expectedToken);
        ResponseEntity<JwtAuthResponse> response = forgetPasswordController.generateToken(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtAuthResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(expectedToken, responseBody.getToken());
        assertEquals("success", responseBody.getMessage());
        verify(customUserDetailsService).loadUserByUsername(email);
        verify(userRepo).findByEmail(email);
        verify(jwtTokenHelper).generateToken(userDetails, user);
    }

    @Test
    public void testGenerateToken_ValidEmail() throws Exception {
        String email = "abc123@example.com";
        String expectedToken = "validToken";
        UserDetails userDetails = mock(UserDetails.class);
        when(customUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(userRepo.findByEmail(email)).thenReturn(user);
        when(jwtTokenHelper.generateToken(userDetails, user)).thenReturn(expectedToken);
        ResponseEntity<JwtAuthResponse> response = forgetPasswordController.generateToken(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtAuthResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(expectedToken, responseBody.getToken());
        assertEquals("success", responseBody.getMessage());
        verify(customUserDetailsService).loadUserByUsername(email);
        verify(userRepo).findByEmail(email);
        verify(jwtTokenHelper).generateToken(userDetails, user);
    }

    @Test
    public void testSendOtp_Success() {
        when(emailService.sendEmail(anyString(), anyString(), eq(user.getEmail()))).thenReturn(true);
        doNothing().when(emailService).saveEmail(eq(user.getEmail()), anyString(), any());
        String response = forgetPasswordController.sendOtp(user);
        assertEquals("OTP has been sent, please verify OTP.", response);
        verify(emailService).sendEmail(anyString(), anyString(), eq(user.getEmail()));
        verify(emailService).saveEmail(eq(user.getEmail()), anyString(), any());
    }

    @Test
    public void testSendOtp_Failure() {
        when(emailService.sendEmail(anyString(), anyString(), eq(user.getEmail()))).thenReturn(false);
        String response = forgetPasswordController.sendOtp(user);
        assertEquals("Please try again..!!", response);
        verify(emailService).sendEmail(anyString(), anyString(), eq(user.getEmail()));
        verify(emailService, never()).saveEmail(eq(user.getEmail()), anyString(), any());
    }

    @Test
    public void testVerifyOtp_Success() throws Exception, InvalidOtpException, OtpExpiredException {
        String otp = "56789";
        String email ="abc123@example.com";;
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setOtp(otp);
        emailVerification.setCreationTime(LocalDateTime.now().minusMinutes(1));
        emailVerification.setStatus("Pending");
        when(emailVerificationRepository.findByEmail(email)).thenReturn(emailVerification);
        when(emailVerificationRepository.save(emailVerification)).thenReturn(emailVerification);
        String response = forgetPasswordController.verifyOtp(otp, email);
        assertEquals("Verified", response);
        verify(emailVerificationRepository).findByEmail(email);
        verify(emailVerificationRepository).save(emailVerification);
    }

    @Test
    public void testVerifyOtp_InvalidOtp() throws Exception {
        String otp = "123456";
        String email = "test@example.com";
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setOtp("56789");
        emailVerification.setCreationTime(LocalDateTime.now().minusMinutes(1));
        emailVerification.setStatus("Pending");
        when(emailVerificationRepository.findByEmail(email)).thenReturn(emailVerification);
        try {
            forgetPasswordController.verifyOtp(otp, email);
        } catch (InvalidOtpException | OtpExpiredException e) {
            assertEquals("Invalid OTP", e.getMessage());
        }
        verify(emailVerificationRepository).findByEmail(email);
        verify(emailVerificationRepository, never()).save(any());
    }


   }