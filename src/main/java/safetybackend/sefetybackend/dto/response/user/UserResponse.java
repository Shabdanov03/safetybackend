package safetybackend.sefetybackend.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Byte age;
    private String email;
    private String phoneNumber1;
    private String phoneNumber2;
    private String city;
    private String address;
    private String password;
}
