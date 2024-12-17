package io.job.my_app.repos;

import io.job.my_app.Entity.SubDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubDomainRepository extends JpaRepository<SubDomain, Integer> {
}

