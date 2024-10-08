package io.job.my_app.dto;


import lombok.Data;

import java.util.Date;
@Data
public class ApplicationDto {
    private Integer applicationId;
    private String applicantName;
    private Date appliedDate;
    private String position;
    private String resumeUrl;
    private Long jobId;

}

