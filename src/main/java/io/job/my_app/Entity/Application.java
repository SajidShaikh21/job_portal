package io.job.my_app.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @Column(nullable = false)
    private String applicantName;




    @Temporal(TemporalType.DATE)
    private Date appliedDate;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String resumeUrl;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private JobPost jobPosting;

}
