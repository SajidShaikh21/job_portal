package io.job.my_app.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Identity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentType; // e.g., "Passport", "ID Card"
    private String fileName;
    private String filePath;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // getters and setters
}

