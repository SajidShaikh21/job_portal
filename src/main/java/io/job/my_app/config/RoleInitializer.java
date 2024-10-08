package io.job.my_app.config;

import io.job.my_app.Entity.Security.Roles;
import io.job.my_app.repos.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Autowired
    private RolesRepo rolesRepo;

    @Bean
    public CommandLineRunner initializeRoles() {
        return args -> {
            if (rolesRepo.findByName("EMPLOYER") == null) {
                Roles employerRole = new Roles();
                employerRole.setName("EMPLOYER");
                rolesRepo.save(employerRole);
            }


            if (rolesRepo.findByName("EMPLOYEE") == null) {
                Roles employeeRole = new Roles();
                employeeRole.setName("EMPLOYEE");
                rolesRepo.save(employeeRole);
            }
            if (rolesRepo.findByName("ADMIN") == null) {
                Roles employeeRole = new Roles();
                employeeRole.setName("ADMIN");
                rolesRepo.save(employeeRole);
            }
        };
    }
}
