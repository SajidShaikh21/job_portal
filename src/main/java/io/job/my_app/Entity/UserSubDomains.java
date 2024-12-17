package io.job.my_app.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class UserSubDomains {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userSubDomainId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sub_domain_id", nullable = false)
    private SubDomain subDomain;


}

