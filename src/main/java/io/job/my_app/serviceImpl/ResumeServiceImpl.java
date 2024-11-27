package io.job.my_app.serviceImpl;

import io.job.my_app.Entity.Resume;
import io.job.my_app.Entity.User;
import io.job.my_app.repos.ResumeRepository;
import io.job.my_app.repos.UserRepo;
import io.job.my_app.service.ResumeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepository;

    public ByteArrayResource getResumeFile(Integer resumeId) throws Exception {
        Optional<Resume> resumeOpt = resumeRepository.findById(resumeId);

        if (!resumeOpt.isPresent()) {
            throw new IllegalArgumentException("Resume not found with id: " + resumeId);
        }
        Resume resume = resumeOpt.get();
        byte[] fileData = resume.getFileData();

        return new ByteArrayResource(fileData);
    }

    @Override
    public Resume uploadResume(Integer userId, MultipartFile file, String education, String experience, String designation, String previousDesignation, String skills) {
        String fileType = file.getContentType();
        if (!isSupportedFileType(fileType)) {
            throw new IllegalArgumentException("Only PDF or DOCX files are allowed.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setEducation(education);
        resume.setExperience(experience);
        resume.setDesignation(designation);
        resume.setPreviousDesignation(previousDesignation);
        resume.setSkills(skills);

        try {
            resume.setFileData(file.getBytes());
            resume.setFileType(fileType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
        return resumeRepository.save(resume);
    }

    @Override
    public Optional<Resume> getResumeById(Long id) {
        return resumeRepository.findById(Math.toIntExact(id));
    }

    @Override
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @Override
    public Resume updateResume(Integer id, MultipartFile file, String education, String experience, String designation, String previousDesignation, String skills) {

        Resume existingResume = resumeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Resume not found with ID: " + id));

        existingResume.setEducation(education);
        existingResume.setExperience(experience);
        existingResume.setDesignation(designation);
        existingResume.setPreviousDesignation(previousDesignation);
        existingResume.setSkills(skills);

        if (file != null && !file.isEmpty()) {
            String fileType = file.getContentType();
            if (!isSupportedFileType(fileType)) {
                throw new IllegalArgumentException("Only PDF or DOCX files are allowed.");
            }
            try {
                existingResume.setFileData(file.getBytes());
                existingResume.setFileType(fileType);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update file", e);
            }
        }
        return resumeRepository.save(existingResume);
    }

    @Override
    public void deleteResume(Integer id) {
        if (!resumeRepository.existsById(id)) {
            throw new IllegalArgumentException("Resume not found with ID: " + id);
        }
        resumeRepository.deleteById(id);
    }

    private boolean isSupportedFileType(String fileType) {
        return "application/pdf".equals(fileType) || "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(fileType);
    }
}
