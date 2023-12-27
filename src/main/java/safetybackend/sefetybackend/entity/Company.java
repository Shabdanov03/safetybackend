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
@Table(name = "companies")
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_gen")
    @SequenceGenerator(name = "company_id_gen", sequenceName = "company_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String phoneNumber;
    private Boolean isActive;
    @OneToOne(cascade = ALL, mappedBy = "company")
    private UserInfo userInfo;
    @OneToMany(cascade = {MERGE, REFRESH, PERSIST, DETACH}, mappedBy = "company")
    private List<User> users;
    @OneToMany(cascade = {REFRESH, DETACH, MERGE, PERSIST}, mappedBy = "company")
    private List<Emergency> emergencies;
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

