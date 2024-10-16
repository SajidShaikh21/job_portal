package io.job.my_app.dto;

import lombok.Data;

@Data
public class PlanDto {

    private Integer planId;
    private String planName;
    private Integer monthlyPostLimit;
    private Integer yearlyPostLimit;
    private Double price;

}
