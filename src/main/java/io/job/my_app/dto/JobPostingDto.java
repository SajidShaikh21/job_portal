package io.job.my_app.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class JobPostingDto {
    private Long jobId;
    private String title;
    private String description;
    private Date postedDate;
    private String status;
    private Long userId;
    private List<Long> laborDomainIds;
    private Long locationId;
}
