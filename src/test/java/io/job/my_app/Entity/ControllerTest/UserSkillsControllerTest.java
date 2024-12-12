package io.job.my_app.Entity.ControllerTest;
import io.job.my_app.controller.UserSkillsController;
import io.job.my_app.dto.UserSkillsDto;
import io.job.my_app.service.UserSkillsService;
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
public class UserSkillsControllerTest {
    @Mock
    private UserSkillsService userSkillsService;

    @InjectMocks
    private UserSkillsController userSkillsController;
    private UserSkillsDto userSkillsDto;

    @BeforeEach
    public void setUp() {
        userSkillsDto = new UserSkillsDto();
        userSkillsDto.setSkillName(".NET");
        userSkillsDto.setDescription("Intermediate");
    }

    @Test
    public void testCreateUserSkill_Success() {
        when(userSkillsService.createUserSkill(any(UserSkillsDto.class))).thenReturn(userSkillsDto);
        ResponseEntity<String> response = userSkillsController.createUserSkill(userSkillsDto);
        assert(response.getStatusCode() == HttpStatus.CREATED);
        assert(response.getBody().equals("User skill created successfully."));
    }

    @Test
    public void testCreateUserSkill_Failure() {
        when(userSkillsService.createUserSkill(any(UserSkillsDto.class))).thenThrow(new RuntimeException("Error creating user skill"));
        ResponseEntity<String> response = userSkillsController.createUserSkill(userSkillsDto);
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Error: Error creating user skill"));
    }

    @Test
    public void testGetUserSkillById_Success() {
        when(userSkillsService.getUserSkillById(anyInt())).thenReturn(userSkillsDto);
        ResponseEntity<?> response = userSkillsController.getUserSkillById(1);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(userSkillsDto));
    }

    @Test
    public void testGetUserSkillById_Failure() {
        when(userSkillsService.getUserSkillById(anyInt())).thenThrow(new RuntimeException("Error fetching user skill"));
        ResponseEntity<?> response = userSkillsController.getUserSkillById(1);
        assert(response.getStatusCode() == HttpStatus.BAD_REQUEST);
        assert(response.getBody().equals("Error fetching user skill: Error fetching user skill"));
    }

    @Test
    public void testGetAllUserSkills_Success() {
        List<UserSkillsDto> skills = Arrays.asList(userSkillsDto);
        when(userSkillsService.getAllUserSkills()).thenReturn(skills);
        ResponseEntity<?> response = userSkillsController.getAllUserSkills();
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(skills));
    }

    @Test
    public void testGetAllUserSkills_Failure() {
        when(userSkillsService.getAllUserSkills()).thenThrow(new RuntimeException("Error fetching user skills"));
        ResponseEntity<?> response = userSkillsController.getAllUserSkills();
        assert(response.getStatusCode() == HttpStatus.BAD_REQUEST);
        assert(response.getBody().equals("Error fetching user skills: Error fetching user skills"));
    }

    @Test
    public void testUpdateUserSkill_Success() {
        when(userSkillsService.updateUserSkill(anyInt(), any(UserSkillsDto.class))).thenReturn(userSkillsDto);
        ResponseEntity<?> response = userSkillsController.updateUserSkill(1, userSkillsDto);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(userSkillsDto));
    }

    @Test
    public void testUpdateUserSkill_Failure() {
        when(userSkillsService.updateUserSkill(anyInt(), any(UserSkillsDto.class))).thenThrow(new RuntimeException("Error updating user skill"));
        ResponseEntity<?> response = userSkillsController.updateUserSkill(1, userSkillsDto);
        assert(response.getStatusCode() == HttpStatus.BAD_REQUEST);
        assert(response.getBody().equals("Error updating user skill: Error updating user skill"));
    }

    @Test
    public void testDeleteUserSkill_Success() {
        doNothing().when(userSkillsService).deleteUserSkill(anyInt());
        ResponseEntity<?> response = userSkillsController.deleteUserSkill(1);
        assert(response.getStatusCode() == HttpStatus.NO_CONTENT);
        assert(response.getBody().equals("User skill deleted successfully."));
    }

    @Test
    public void testDeleteUserSkill_Failure() {
        doThrow(new RuntimeException("Error deleting user skill")).when(userSkillsService).deleteUserSkill(anyInt());
        ResponseEntity<?> response = userSkillsController.deleteUserSkill(1);
        assert(response.getStatusCode() == HttpStatus.BAD_REQUEST);
        assert(response.getBody().equals("Error deleting user skill: Error deleting user skill"));
    }
}
