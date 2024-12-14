package io.job.my_app.Entity.ControllerTest;
import io.job.my_app.Entity.Security.EmailVerification;
import io.job.my_app.controller.UserController;
import io.job.my_app.dto.UserDto;
import io.job.my_app.exception.*;
import io.job.my_app.repos.EmailVerificationRepository;
import io.job.my_app.service.EmailService;
import io.job.my_app.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private EmailService emailService;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @Test
    public void testSendOtp_success() {
        UserDto userDto = new UserDto();
        userDto.setEmail("User456@example.com");
        when(emailService.sendEmail(anyString(), anyString(), eq("User456@example.com"))).thenReturn(true);
        String response = userController.sendOtp(userDto);
        assertEquals("OTP has been sent, please verify OTP.", response);
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), eq("User456@example.com"));
        verify(emailService, times(1)).saveEmail(eq("User456@example.com"), anyString(), any(LocalDateTime.class));
    }

    @Test
    public void testSendOtp_failure() {
        UserDto userDto = new UserDto();
        userDto.setEmail("User456@example.com");
        when(emailService.sendEmail(anyString(), anyString(), eq("User456@example.com"))).thenReturn(false);
        String response = userController.sendOtp(userDto);
        assertEquals("Please try again..!!", response);
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), eq("User456@example.com"));
    }

    @Test
    public void testVerifyOtp_success() throws OtpExpiredException, InvalidOtpException {
        String otp = "123456";
        String email ="User456@example.com";
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setOtp(otp);
        emailVerification.setEmail(email);
        emailVerification.setCreationTime(LocalDateTime.now().minusMinutes(2));
        emailVerification.setStatus("Unverified");
        when(emailVerificationRepository.findByEmail(email)).thenReturn(emailVerification);
        String response = userController.verifyOtp(otp, email);
        assertEquals("Verified", response);
        verify(emailVerificationRepository, times(1)).save(emailVerification);
    }

    @Test
    public void testVerifyOtp_invalidOtp() {
        String otp = "wrongOtp";
        String email = "User456@example.com";
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setOtp("123456");
        emailVerification.setEmail(email);
        emailVerification.setCreationTime(LocalDateTime.now().minusMinutes(2));
        when(emailVerificationRepository.findByEmail(email)).thenReturn(emailVerification);
        assertThrows(InvalidOtpException.class, () -> userController.verifyOtp(otp, email));
    }

    @Test
    public void testVerifyOtp_otpExpired() {
        String otp = "123456";
        String email ="User456@example.com";
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setOtp(otp);
        emailVerification.setEmail(email);
        emailVerification.setCreationTime(LocalDateTime.now().minusMinutes(5));
        emailVerification.setStatus("Unverified");
        when(emailVerificationRepository.findByEmail(email)).thenReturn(emailVerification);
        assertThrows(OtpExpiredException.class, () -> userController.verifyOtp(otp, email));
    }

    @Test
    public void testVerifyOtp_emailNotFound() {
        String otp = "123456";
        String email = "89@example.com";
        when(emailVerificationRepository.findByEmail(email)).thenReturn(null);
        assertThrows(InvalidOtpException.class, () -> userController.verifyOtp(otp, email));
    }
    @Test
    public void testRegisterUser_success() throws UserAlreadyExistException, InvalidPasswordException {
        UserDto userDto = new UserDto();
        userDto.setEmail("User456@example.com");
        userDto.setPassword("password123");
        userDto.setConfirmPassword("password123");
        userDto.setRole("EMPLOYEE");
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(userDto.getEmail());
        emailVerification.setStatus("Verified");
        when(emailVerificationRepository.findByEmail(userDto.getEmail())).thenReturn(emailVerification);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userService.createUser(userDto)).thenReturn(userDto);
        ResponseEntity<String> response = userController.registerUser(userDto, mock(HttpSession.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(userService, times(1)).createUser(userDto);
    }
    @Test
    public void testRegisterUser_passwordMismatch() {
        UserDto userDto = new UserDto();
        userDto.setEmail("User456@example.com");
        userDto.setPassword("password123");
        userDto.setConfirmPassword("password124");
        assertThrows(InvalidPasswordException.class, () -> userController.registerUser(userDto, mock(HttpSession.class)));
    }

    @Test
    public void testRegisterUser_invalidRole() throws UserAlreadyExistException, InvalidPasswordException {
        UserDto userDto = new UserDto();
        userDto.setEmail("User456@example.com");
        userDto.setPassword("password123");
        userDto.setConfirmPassword("password123");
        userDto.setRole("INVALID_ROLE");
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(userDto.getEmail());
        emailVerification.setStatus("Verified");
        when(emailVerificationRepository.findByEmail(userDto.getEmail())).thenReturn(emailVerification);
        ResponseEntity<String> response = userController.registerUser(userDto, mock(HttpSession.class));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid role. Must be either EMPLOYER or EMPLOYEE.", response.getBody());
    }

    @Test
    public void testUpdateUser_success() {
        Integer userId = 1;
        UserDto userDto = new UserDto();
        userDto.setEmail("User456@example.com");
        userDto.setName("Neha");
        userDto.setAddress("Pune");
        when(userService.updateUser(any(UserDto.class), eq(userId))).thenReturn(userDto);
        ResponseEntity<UserDto> response = userController.updateUser(userDto, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).updateUser(any(UserDto.class), eq(userId));
    }

    @Test
    public void testUpdateUser_userNotFound() {
        Integer userId = 1;
        UserDto userDto = new UserDto();
        userDto.setEmail("*94user@example.com");
        when(userService.updateUser(any(UserDto.class), eq(userId))).thenThrow(new UsernameNotFoundException("User not found"));
        assertThrows(UsernameNotFoundException.class, () -> userController.updateUser(userDto, userId));
        verify(userService, times(1)).updateUser(any(UserDto.class), eq(userId));
    }

    @Test
    public void testDeleteUser() throws ResourceNotFoundException {
        Integer userId = 1;
        doNothing().when(userService).deleteUser(userId);
        ResponseEntity<Void> response = userController.deleteUser(userId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
    @Test
    public void testDeleteUser_success() throws ResourceNotFoundException {
        Integer userId = 1;
        doNothing().when(userService).deleteUser(userId);
        ResponseEntity<Void> response = userController.deleteUser(userId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testDeleteUser_userNotFound() throws ResourceNotFoundException {
        Integer userId = 1;
        doThrow(new ResourceNotFoundException("User not found")).when(userService).deleteUser(userId);
        assertThrows(ResourceNotFoundException.class, () -> userController.deleteUser(userId));
        verify(userService, times(1)).deleteUser(userId);
    }
}