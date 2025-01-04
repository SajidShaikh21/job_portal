package io.job.my_app.service;

import io.job.my_app.dto.SubDomainDto;
import java.util.List;

public interface SubDomainService {
    SubDomainDto createSubDomain(SubDomainDto subDomainDto);
    SubDomainDto getSubDomainById(Integer subDomainId);
    List<SubDomainDto> getAllSubDomains();
    SubDomainDto updateSubDomain(Integer subDomainId, SubDomainDto subDomainDto);
    void deleteSubDomain(Integer subDomainId);
}
