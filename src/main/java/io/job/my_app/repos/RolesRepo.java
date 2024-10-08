package io.job.my_app.repos;


import io.job.my_app.Entity.Security.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Roles,Integer> {

    Roles findByName(String name);
}
