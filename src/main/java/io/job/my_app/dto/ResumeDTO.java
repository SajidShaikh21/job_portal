package io.job.my_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDTO {
    private Integer id;
    private String education;
    private String experience;
    private String filePath;
    private Long userId;
    private String designation;
    private String previousDesignation;
    private String skills;
    private byte[] fileData;
    private String fileType;
}
