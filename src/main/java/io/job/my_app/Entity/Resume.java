package io.job.my_app.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User who uploaded the resume

    @ManyToOne
    @JoinColumn(name = "job_post_id", nullable = true)
    private JobPost jobPost; // Job post the resume is applying for

}

