package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.SubDomain;
import io.job.my_app.dto.SubDomainDto;
import io.job.my_app.exception.SubDomainNotFoundException;
import io.job.my_app.repos.SubDomainRepository;
import io.job.my_app.service.SubDomainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubDomainServiceImpl implements SubDomainService {

    @Autowired
    private SubDomainRepository subDomainRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SubDomainDto createSubDomain(SubDomainDto subDomainDto) {
        try {
            SubDomain subDomain = modelMapper.map(subDomainDto, SubDomain.class);
            SubDomain savedSubDomain = subDomainRepository.save(subDomain);
            return modelMapper.map(savedSubDomain, SubDomainDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating subdomain: " + e.getMessage(), e);
        }
    }

    @Override
    public SubDomainDto getSubDomainById(Integer subDomainId) {
        try {
            SubDomain subDomain = subDomainRepository.findById(subDomainId)
                    .orElseThrow(() -> new SubDomainNotFoundException("SubDomain not found with ID: " + subDomainId));
            return modelMapper.map(subDomain, SubDomainDto.class);
        } catch (SubDomainNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching subdomain: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SubDomainDto> getAllSubDomains() {
        try {
            List<SubDomain> subDomains = subDomainRepository.findAll();
            return subDomains.stream()
                    .map(subDomain -> modelMapper.map(subDomain, SubDomainDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all subdomains: " + e.getMessage(), e);
        }
    }

    @Override
    public SubDomainDto updateSubDomain(Integer subDomainId, SubDomainDto subDomainDto) {
        try {
            SubDomain existingSubDomain = subDomainRepository.findById(subDomainId)
                    .orElseThrow(() -> new SubDomainNotFoundException("SubDomain not found with ID: " + subDomainId));

            existingSubDomain.setSubDomainName(subDomainDto.getSubDomainName());
            SubDomain updatedSubDomain = subDomainRepository.save(existingSubDomain);

            return modelMapper.map(updatedSubDomain, SubDomainDto.class);
        } catch (SubDomainNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error updating subdomain: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteSubDomain(Integer subDomainId) {
        try {
            SubDomain subDomain = subDomainRepository.findById(subDomainId)
                    .orElseThrow(() -> new SubDomainNotFoundException("SubDomain not found with ID: " + subDomainId));
            subDomainRepository.delete(subDomain);
        } catch (SubDomainNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting subdomain: " + e.getMessage(), e);
        }
    }
}


