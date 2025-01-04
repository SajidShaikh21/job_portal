package io.job.my_app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DomainDto {
    private Integer domainId;
    private String domainName;
}

