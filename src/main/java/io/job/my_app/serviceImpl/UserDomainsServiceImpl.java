package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.Domain;
import io.job.my_app.Entity.User;
import io.job.my_app.Entity.UserDomains;
import io.job.my_app.dto.UserDomainsDto;

import io.job.my_app.repos.DomainRepository;
import io.job.my_app.repos.UserDomainsRepository;
import io.job.my_app.repos.UserRepo;
import io.job.my_app.service.UserDomainsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDomainsServiceImpl implements UserDomainsService {

    @Autowired
    private UserDomainsRepository userDomainsRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DomainRepository domainRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserDomainsServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Custom mapping for UserDomains
        this.modelMapper.addMappings(new PropertyMap<UserDomains, UserDomainsDto>() {
            @Override
            protected void configure() {
                map(source.getUser().getUserId(), destination.getUserId());
                map(source.getDomain().getDomainId(), destination.getDomainId());
            }
        });
    }

    @Override
    public UserDomainsDto createUserDomain(UserDomainsDto userDomainsDto) {
        try {
            User user = userRepo.findById(userDomainsDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userDomainsDto.getUserId()));
            Domain domain = domainRepository.findById(userDomainsDto.getDomainId())
                    .orElseThrow(() -> new RuntimeException("Domain not found with ID: " + userDomainsDto.getDomainId()));

            UserDomains userDomains = new UserDomains();
            userDomains.setUser(user);
            userDomains.setDomain(domain);

            UserDomains savedUserDomain = userDomainsRepository.save(userDomains);
            return modelMapper.map(savedUserDomain, UserDomainsDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating UserDomain: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDomainsDto getUserDomainById(Integer id) {
        try {
            UserDomains userDomains = userDomainsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UserDomain not found with ID: " + id));
            return modelMapper.map(userDomains, UserDomainsDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching UserDomain: " + e.getMessage(), e);
        }
    }

    @Override
    public List<UserDomainsDto> getAllUserDomains() {
        try {
            List<UserDomains> userDomainsList = userDomainsRepository.findAll();
            return userDomainsList.stream()
                    .map(userDomain -> modelMapper.map(userDomain, UserDomainsDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all UserDomains: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDomainsDto updateUserDomain(Integer id, UserDomainsDto userDomainsDto) {
        try {
            UserDomains existingUserDomain = userDomainsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UserDomain not found with ID: " + id));

            User user = userRepo.findById(userDomainsDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userDomainsDto.getUserId()));
            Domain domain = domainRepository.findById(userDomainsDto.getDomainId())
                    .orElseThrow(() -> new RuntimeException("Domain not found with ID: " + userDomainsDto.getDomainId()));

            existingUserDomain.setUser(user);
            existingUserDomain.setDomain(domain);

            UserDomains updatedUserDomain = userDomainsRepository.save(existingUserDomain);
            return modelMapper.map(updatedUserDomain, UserDomainsDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error updating UserDomain: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUserDomain(Integer id) {
        try {
            UserDomains userDomains = userDomainsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UserDomain not found with ID: " + id));
            userDomainsRepository.delete(userDomains);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting UserDomain: " + e.getMessage(), e);
        }
    }
}


