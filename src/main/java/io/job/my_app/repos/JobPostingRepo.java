package io.job.my_app.repos;

import io.job.my_app.Entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface JobPostingRepo extends JpaRepository<JobPost,Integer> {

    Optional<JobPost> findById(Integer jobPostId);
}
