package io.job.my_app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class UserSkillsDto {
    private Integer skillId;
    private Integer userId;
    private String skillName;
    private String description;
}
