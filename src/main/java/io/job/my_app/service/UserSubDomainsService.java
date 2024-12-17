package io.job.my_app.service;

import io.job.my_app.dto.UserSubDomainsDto;

import java.util.List;

public interface UserSubDomainsService {
    UserSubDomainsDto createUserSubDomain(UserSubDomainsDto dto);
    UserSubDomainsDto getUserSubDomainById(Integer id);
    List<UserSubDomainsDto> getAllUserSubDomains();
    UserSubDomainsDto updateUserSubDomain(Integer id, UserSubDomainsDto dto);
    void deleteUserSubDomain(Integer id);
}


