package io.job.my_app.repos;


import io.job.my_app.Entity.EmployerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployerProfileRepository
        extends JpaRepository<EmployerProfile, Integer>, JpaSpecificationExecutor<EmployerProfile> {
}
