package safetybackend.sefetybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import safetybackend.sefetybackend.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @OneToOne(cascade = ALL, mappedBy = "user")
    private UserInfo userInfo;
    @OneToOne(cascade = ALL, mappedBy = "user")
    private Address address;
    @OneToOne(cascade = ALL, mappedBy = "user")
    private Balance balance;
    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Company company;
    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Emergency emergency;
    @Enumerated(STRING)
    private UserStatus userStatus;
    private Boolean isActive;
    private String image;
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
