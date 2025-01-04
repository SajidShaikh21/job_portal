package io.job.my_app.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SubDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subDomainId;

    @Column(nullable = false, length = 100)
    private String subDomainName;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;

}

