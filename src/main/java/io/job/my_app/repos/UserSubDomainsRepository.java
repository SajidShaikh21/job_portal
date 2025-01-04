package io.job.my_app.repos;


import io.job.my_app.Entity.UserSubDomains;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubDomainsRepository extends JpaRepository<UserSubDomains, Integer> {
}
