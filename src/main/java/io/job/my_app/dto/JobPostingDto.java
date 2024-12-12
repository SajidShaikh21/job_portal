package io.job.my_app.dto;
import io.job.my_app.Entity.Security.Status;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobPostingDto {
    private Integer jobId;
    private String title;
    private String description;
    private LocalDate postedDate;
    private Status status;
    private Integer userId;
    private Integer locationId;
    private List<Integer> applicationIds;
}