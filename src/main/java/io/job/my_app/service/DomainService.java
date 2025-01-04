package io.job.my_app.service;

import io.job.my_app.dto.DomainDto;

import java.util.List;

public interface DomainService {
    DomainDto createDomain(DomainDto domainDto);
    DomainDto getDomainById(Integer domainId);
    List<DomainDto> getAllDomains();
    DomainDto updateDomain(Integer domainId, DomainDto domainDto);
    void deleteDomain(Integer domainId);
}

