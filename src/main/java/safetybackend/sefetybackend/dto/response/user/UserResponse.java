package safetybackend.sefetybackend.dto.response.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import safetybackend.sefetybackend.enums.Role;
import safetybackend.sefetybackend.enums.UserStatus;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String image;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String phoneNumber1;
    private String phoneNumber2;
    private String city;
    private String address;
    private String companyName;
}
