package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.Domain;
import io.job.my_app.dto.DomainDto;

import io.job.my_app.exception.DomainNotFoundException;

import io.job.my_app.repos.DomainRepository;
import io.job.my_app.service.DomainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DomainDto createDomain(DomainDto domainDto) {
        try {
            Domain domain = modelMapper.map(domainDto, Domain.class);
            Domain savedDomain = domainRepository.save(domain);
            return modelMapper.map(savedDomain, DomainDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating domain: " + e.getMessage(), e);
        }
    }

    @Override
    public DomainDto getDomainById(Integer domainId) {
        try {
            Domain domain = domainRepository.findById(domainId)
                    .orElseThrow(() -> new DomainNotFoundException("Domain not found with ID: " + domainId));
            return modelMapper.map(domain, DomainDto.class);
        } catch (DomainNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching domain: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DomainDto> getAllDomains() {
        try {
            List<Domain> domains = domainRepository.findAll();
            return domains.stream()
                    .map(domain -> modelMapper.map(domain, DomainDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all domains: " + e.getMessage(), e);
        }
    }

    @Override
    public DomainDto updateDomain(Integer domainId, DomainDto domainDto) {
        try {
            Domain existingDomain = domainRepository.findById(domainId)
                    .orElseThrow(() -> new DomainNotFoundException("Domain not found with ID: " + domainId));

            existingDomain.setDomainName(domainDto.getDomainName());
            Domain updatedDomain = domainRepository.save(existingDomain);

            return modelMapper.map(updatedDomain, DomainDto.class);
        } catch (DomainNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error updating domain: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteDomain(Integer domainId) {
        try {
            Domain domain = domainRepository.findById(domainId)
                    .orElseThrow(() -> new DomainNotFoundException("Domain not found with ID: " + domainId));
            domainRepository.delete(domain);
        } catch (DomainNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting domain: " + e.getMessage(), e);
        }
    }
}


