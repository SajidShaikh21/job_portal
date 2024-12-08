package io.job.my_app.serviceImpl;


import io.job.my_app.Entity.User;
import io.job.my_app.Entity.UserSkills;
import io.job.my_app.dto.UserSkillsDto;
import io.job.my_app.repos.UserRepo;
import io.job.my_app.repos.UserSkillsRepository;
import io.job.my_app.service.UserSkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSkillsServiceImpl implements UserSkillsService {

    @Autowired
    private UserSkillsRepository userSkillsRepository;

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserSkillsDto createUserSkill(UserSkillsDto userSkillsDTO) {
        // Validate if the user exists before adding the skill
        Integer userId = userSkillsDTO.getUserId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Cannot add skill: User with ID " + userId + " does not exist."));

        // Proceed to save the skill if the user exists
        UserSkills userSkills = mapToEntity(userSkillsDTO);
        userSkills.setUser(user);
        UserSkills savedSkill = userSkillsRepository.save(userSkills);
        return mapToDTO(savedSkill);
    }

    @Override
    public UserSkillsDto getUserSkillById(Integer skillId) {
        UserSkills userSkills = userSkillsRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + skillId));
        return mapToDTO(userSkills);
    }

    @Override
    public List<UserSkillsDto> getAllUserSkills() {
        List<UserSkills> skills = userSkillsRepository.findAll();
        return skills.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UserSkillsDto updateUserSkill(Integer skillId, UserSkillsDto userSkillsDTO) {
        UserSkills existingSkill = userSkillsRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + skillId));

        // Update fields
        existingSkill.setSkillName(userSkillsDTO.getSkillName());
        existingSkill.setDescription(userSkillsDTO.getDescription());

        // Update user association if needed
        if (!existingSkill.getUser().getUserId().equals(userSkillsDTO.getUserId())) {
            Integer userId = userSkillsDTO.getUserId();
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID " + userId + " does not exist."));
            existingSkill.setUser(user);
        }

        UserSkills updatedSkill = userSkillsRepository.save(existingSkill);
        return mapToDTO(updatedSkill);
    }

    @Override
    public void deleteUserSkill(Integer skillId) {
        if (!userSkillsRepository.existsById(skillId)) {
            throw new RuntimeException("Skill not found with ID: " + skillId);
        }
        userSkillsRepository.deleteById(skillId);
    }

    private UserSkillsDto mapToDTO(UserSkills userSkills) {
        UserSkillsDto dto = new UserSkillsDto();
        dto.setSkillId(userSkills.getSkillId());
        dto.setUserId(userSkills.getUser().getUserId());
        dto.setSkillName(userSkills.getSkillName());
        dto.setDescription(userSkills.getDescription());
        return dto;
    }

    private UserSkills mapToEntity(UserSkillsDto dto) {
        UserSkills userSkills = new UserSkills();
        userSkills.setSkillId(dto.getSkillId());
        userSkills.setSkillName(dto.getSkillName());
        userSkills.setDescription(dto.getDescription());
        return userSkills;
    }
}
