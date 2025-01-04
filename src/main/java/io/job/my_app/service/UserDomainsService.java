package io.job.my_app.service;

import io.job.my_app.dto.UserDomainsDto;
import java.util.List;

public interface UserDomainsService {
    UserDomainsDto createUserDomain(UserDomainsDto userDomainsDto);
    UserDomainsDto getUserDomainById(Integer id);
    List<UserDomainsDto> getAllUserDomains();
    UserDomainsDto updateUserDomain(Integer id, UserDomainsDto userDomainsDto);
    void deleteUserDomain(Integer id);
}

