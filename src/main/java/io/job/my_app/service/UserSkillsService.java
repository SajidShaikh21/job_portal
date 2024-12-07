package io.job.my_app.service;



import io.job.my_app.dto.UserSkillsDto;

import java.util.List;

public interface UserSkillsService {
    UserSkillsDto createUserSkill(UserSkillsDto userSkillsDTO);
    UserSkillsDto getUserSkillById(Integer skillId);
    List<UserSkillsDto> getAllUserSkills();
    UserSkillsDto updateUserSkill(Integer skillId, UserSkillsDto userSkillsDTO);
    void deleteUserSkill(Integer skillId);
}


