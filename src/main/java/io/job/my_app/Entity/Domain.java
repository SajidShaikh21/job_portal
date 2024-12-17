package io.job.my_app.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity

@Getter
@Setter

public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer domainId;

    @Column(nullable = false, length = 50)
    private String domainName;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubDomain> subDomains;

}
