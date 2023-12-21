package safetybackend.sefetybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import safetybackend.sefetybackend.enums.Role;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_infos")
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_id_gen")
    @SequenceGenerator(name = "user_info_id_gen", sequenceName = "user_info_id_seq", allocationSize = 1)
    private Long id;
    private String email;
    private String password;
    private String resetPasswordToken;
    @Enumerated(STRING)
    private Role role;
    @OneToOne(cascade = ALL)
    private User user;
    @OneToOne(cascade = ALL)
    private Company company;
}
