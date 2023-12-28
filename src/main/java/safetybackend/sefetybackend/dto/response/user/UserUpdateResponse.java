package safetybackend.sefetybackend.dto.response.user;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String image;
    private String email;
    private String password;
    private String phoneNumber1;
    private String phoneNumber2;
    private String city;
    private String address;
}
