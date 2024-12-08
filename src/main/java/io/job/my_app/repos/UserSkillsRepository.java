package io.job.my_app.repos;

import io.job.my_app.Entity.UserSkills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSkillsRepository extends JpaRepository<UserSkills, Integer> {
}
