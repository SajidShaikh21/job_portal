package io.job.my_app.Entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class UserDomains {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userDomainId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;


}


