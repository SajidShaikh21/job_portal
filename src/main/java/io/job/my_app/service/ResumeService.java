package io.job.my_app.service;

import io.job.my_app.Entity.Resume;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public interface ResumeService {

    String RESUME_STORAGE_PATH = "C:\\Users\\ajrra\\Downloads";

    default byte[] getFileContentByFileName(String fileName) throws IOException {
        Path filePath = Paths.get(RESUME_STORAGE_PATH, fileName);
        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        }
        return null;
    }

    Resume uploadResume(Integer userId, MultipartFile file, String education, String experience,
                        String designation, String previousDesignation, String skills);


    Optional<Resume> getResumeById(Long id);

    List<Resume> getAllResumes();


    Resume updateResume(Integer id, MultipartFile file, String education, String experience,
                        String designation, String previousDesignation, String skills);

    void deleteResume(Integer id);

    ByteArrayResource getResumeFile(Integer id) throws Exception;
}
