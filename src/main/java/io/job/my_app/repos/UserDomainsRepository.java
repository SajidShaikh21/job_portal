package io.job.my_app.repos;


import io.job.my_app.Entity.UserDomains;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDomainsRepository extends JpaRepository<UserDomains, Integer> {
}
