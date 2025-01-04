package io.job.my_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubDomainDto {
    private Integer subDomainId;
    private String subDomainName;
    private Integer domainId;
}

