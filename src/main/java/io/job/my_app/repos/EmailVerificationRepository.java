package io.job.my_app.repos;


import io.job.my_app.Entity.Security.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification,Integer> {


    EmailVerification findByEmail(String email);
}
