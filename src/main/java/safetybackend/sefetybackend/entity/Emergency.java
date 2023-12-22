package safetybackend.sefetybackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "emergencies")
@Entity
public class Emergency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emergency_id_gen")
    @SequenceGenerator(name = "emergency_id_gen", sequenceName = "emergency_id_seq", allocationSize = 1)
    private Long id;
    private String userLocationName;
    private Double userLat;
    private Double userLong;
    private Double companyLat;
    private Double companyLong;
    @OneToMany(cascade = {REFRESH, DETACH, MERGE, PERSIST}, mappedBy = "emergency")
    private List<User> users;
    @ManyToMany(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private List<Company> companies;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    @PrePersist
    protected void onCreate() {
        modifiedAt = LocalDateTime.now();
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}

