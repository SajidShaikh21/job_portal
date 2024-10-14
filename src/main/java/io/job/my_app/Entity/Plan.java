package io.job.my_app.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;

    @Column(nullable = false)
    private String planName;

    @Column(nullable = false)
    private Integer monthlyPostLimit;

    @Column(nullable = false)
    private Integer yearlyPostLimit;

    @Column(nullable = false)
    private Double price;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Employer who purchased the plan

}

