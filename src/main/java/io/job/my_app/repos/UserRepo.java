package io.job.my_app.repos;


import io.job.my_app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);

   // List<User> findByBrokerProfiles_BrokerProfileId(Integer brokerProfileId);
}
