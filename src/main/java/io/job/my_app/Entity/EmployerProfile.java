package io.job.my_app.Entity;

import io.job.my_app.Entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employer_profile")
public class EmployerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Employer_ID")
    private Integer employerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @Column(name = "Employer_Name", length = 50, nullable = false)
    private String employerName;

    @Column(name = "industry", length = 200)
    private String industry;

    @Column(name = "city", length = 50)
    private String city;

}

