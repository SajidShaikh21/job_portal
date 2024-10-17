package io.job.my_app.dto;

import io.job.my_app.Entity.Security.Status;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobPostingDto {
    private Integer jobId;
    private String title;
    private String description;
    private Date postedDate;
    private Status status;
    private Integer userId;
//    private List<ApplicationDto> applications;
//    private List<LaborDomainDTO> laborDomains;
//    private LocationDTO location;
//
}