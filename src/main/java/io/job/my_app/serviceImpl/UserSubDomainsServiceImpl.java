package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.SubDomain;
import io.job.my_app.Entity.User;
import io.job.my_app.Entity.UserSubDomains;
import io.job.my_app.dto.UserSubDomainsDto;
import io.job.my_app.exception.UserSubDomainsNotFoundException;
import io.job.my_app.repos.UserRepo;
import io.job.my_app.repos.UserSubDomainsRepository;
import io.job.my_app.repos.UserRepo;
import io.job.my_app.repos.SubDomainRepository;
import io.job.my_app.service.UserSubDomainsService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserSubDomainsServiceImpl implements UserSubDomainsService {

    @Autowired
    private UserSubDomainsRepository userSubDomainsRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubDomainRepository subDomainRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<UserSubDomains, UserSubDomainsDto>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getUserId());
                map().setSubDomainId(source.getSubDomain().getSubDomainId());
            }
        });
    }

    @Override
    public UserSubDomainsDto createUserSubDomain(UserSubDomainsDto userSubDomainsDto) {
        try {
            UserSubDomains userSubDomain = modelMapper.map(userSubDomainsDto, UserSubDomains.class);
            UserSubDomains savedUserSubDomain = userSubDomainsRepository.save(userSubDomain);
            return modelMapper.map(savedUserSubDomain, UserSubDomainsDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating UserSubDomain: " + e.getMessage(), e);
        }
    }

    @Override
    public UserSubDomainsDto getUserSubDomainById(Integer userSubDomainId) {
        try {
            UserSubDomains userSubDomain = userSubDomainsRepository.findById(userSubDomainId)
                    .orElseThrow(() -> new UserSubDomainsNotFoundException("UserSubDomain not found with ID: " + userSubDomainId));
            return modelMapper.map(userSubDomain, UserSubDomainsDto.class);
        } catch (UserSubDomainsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching UserSubDomain: " + e.getMessage(), e);
        }
    }

    @Override
    public List<UserSubDomainsDto> getAllUserSubDomains() {
        try {
            List<UserSubDomains> userSubDomainsList = userSubDomainsRepository.findAll();
            return userSubDomainsList.stream()
                    .map(userSubDomain -> modelMapper.map(userSubDomain, UserSubDomainsDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all UserSubDomains: " + e.getMessage(), e);
        }
    }

    @Override
    public UserSubDomainsDto updateUserSubDomain(Integer userSubDomainId, UserSubDomainsDto userSubDomainsDto) {
        try {
            // Fetch the existing UserSubDomain entity
            UserSubDomains existingUserSubDomain = userSubDomainsRepository.findById(userSubDomainId)
                    .orElseThrow(() -> new UserSubDomainsNotFoundException("UserSubDomain not found with ID: " + userSubDomainId));

            // Validate userId and subDomainId
            if (userSubDomainsDto.getUserId() == null || userSubDomainsDto.getSubDomainId() == null) {
                throw new IllegalArgumentException("User ID and SubDomain ID cannot be null");
            }

            // Fetch the User and SubDomain entities based on the IDs
            Optional<User> userOptional = userRepo.findById(userSubDomainsDto.getUserId());
            Optional<SubDomain> subDomainOptional = subDomainRepository.findById(userSubDomainsDto.getSubDomainId());

            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("User with ID " + userSubDomainsDto.getUserId() + " not found");
            }
            if (subDomainOptional.isEmpty()) {
                throw new IllegalArgumentException("SubDomain with ID " + userSubDomainsDto.getSubDomainId() + " not found");
            }

            // Set the User and SubDomain entities to the existing UserSubDomain entity
            existingUserSubDomain.setUser(userOptional.get());
            existingUserSubDomain.setSubDomain(subDomainOptional.get());

            // Save the updated UserSubDomain entity
            UserSubDomains updatedUserSubDomain = userSubDomainsRepository.save(existingUserSubDomain);

            // Return the updated UserSubDomain DTO
            return modelMapper.map(updatedUserSubDomain, UserSubDomainsDto.class);
        } catch (UserSubDomainsNotFoundException | IllegalArgumentException e) {
            throw e;  // Rethrow if UserSubDomain, User, or SubDomain not found
        } catch (Exception e) {
            throw new RuntimeException("Error updating UserSubDomain: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUserSubDomain(Integer userSubDomainId) {
        try {
            UserSubDomains userSubDomain = userSubDomainsRepository.findById(userSubDomainId)
                    .orElseThrow(() -> new UserSubDomainsNotFoundException("UserSubDomain not found with ID: " + userSubDomainId));
            userSubDomainsRepository.delete(userSubDomain);
        } catch (UserSubDomainsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting UserSubDomain: " + e.getMessage(), e);
        }
    }
}



