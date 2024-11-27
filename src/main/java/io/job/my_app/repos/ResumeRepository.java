package io.job.my_app.repos;
import io.job.my_app.Entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    Optional<Resume> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);
}
