package io.job.my_app.service;


import io.job.my_app.dto.EmployerProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployerProfileService {

    EmployerProfileDto createEmployerProfile(EmployerProfileDto dto);

    EmployerProfileDto updateEmployerProfile(Integer id, EmployerProfileDto dto);

    EmployerProfileDto getEmployerProfileById(Integer id);

    void deleteEmployerProfile(Integer id);

    Page<EmployerProfileDto> getAllEmployerProfiles(Pageable pageable);
}
