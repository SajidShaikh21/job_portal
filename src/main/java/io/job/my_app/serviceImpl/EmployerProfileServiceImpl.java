package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.EmployerProfile;
import io.job.my_app.dto.EmployerProfileDto;
import io.job.my_app.exception.EmployerProfileNotFoundException;
import io.job.my_app.repos.EmployerProfileRepository;
import io.job.my_app.service.EmployerProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployerProfileServiceImpl implements EmployerProfileService {

    @Autowired
    private EmployerProfileRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EmployerProfileDto createEmployerProfile(EmployerProfileDto dto) {
        // Map the DTO to the entity
        EmployerProfile employerProfile = modelMapper.map(dto, EmployerProfile.class);

        // Save the entity
        EmployerProfile savedProfile = repository.save(employerProfile);

        // Map the saved entity back to the DTO
        return modelMapper.map(savedProfile, EmployerProfileDto.class);
    }

    @Override
    public EmployerProfileDto getEmployerProfileById(Integer id) {
        // Retrieve the employer profile or throw an exception if not found
        EmployerProfile employerProfile = repository.findById(id)
                .orElseThrow(() -> new EmployerProfileNotFoundException(
                        "EmployerProfile with ID " + id + " not found"));

        // Map the entity to the DTO
        return modelMapper.map(employerProfile, EmployerProfileDto.class);
    }

    @Override
    public EmployerProfileDto updateEmployerProfile(Integer id, EmployerProfileDto dto) {
        // Retrieve the existing profile or throw an exception
        EmployerProfile existingProfile = repository.findById(id)
                .orElseThrow(() -> new EmployerProfileNotFoundException(
                        "EmployerProfile with ID " + id + " not found"));

        // Update fields
        existingProfile.setEmployerName(dto.getEmployerName());
        existingProfile.setIndustry(dto.getIndustry());
        existingProfile.setCity(dto.getCity());

        // Save the updated profile
        EmployerProfile updatedProfile = repository.save(existingProfile);

        // Map the updated entity to a DTO
        return modelMapper.map(updatedProfile, EmployerProfileDto.class);
    }

    @Override
    public void deleteEmployerProfile(Integer id) {
        // Retrieve the existing profile or throw an exception
        EmployerProfile existingProfile = repository.findById(id)
                .orElseThrow(() -> new EmployerProfileNotFoundException(
                        "EmployerProfile with ID " + id + " not found"));

        // Delete the profile
        repository.delete(existingProfile);
    }

    @Override
    public Page<EmployerProfileDto> getAllEmployerProfiles(Pageable pageable) {
        // Retrieve all profiles with pagination and map to DTOs
        return repository.findAll(pageable).map(profile -> modelMapper.map(profile, EmployerProfileDto.class));
    }
}
