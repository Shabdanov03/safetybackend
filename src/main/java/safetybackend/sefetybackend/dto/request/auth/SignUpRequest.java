package safetybackend.sefetybackend.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "The first name must not be empty.")
    @NotNull(message = "The first name must not be empty.")
    private String firstName;

    @NotBlank(message = "The last name must not be empty.")
    @NotNull(message = "The last name must not be empty.")
    private String lastName;

    @NotNull(message = "The age must not be empty.")
    private Byte age;

    @NotBlank(message = "The email must not be empty.")
    @NotNull(message = "The email must not be empty.")
    @Email(message = "Sorry, the email address you entered is invalid. Please check if it is correct")
    private String email;

    @NotBlank(message = "The password must not be empty.")
    @NotNull(message = "The password must not be empty.")
    private String password;

    @NotBlank(message = "The first phone number must not be empty.")
    @NotNull(message = "The first phone number must not be empty.")
    private String phoneNumber1;
    private String phoneNumber2;

    @NotNull(message = "The city must not be empty.")
    private String city;

    @NotNull(message = "The address must not be empty.")
    private String address;

    @NotNull(message = "The image must not be empty.")
    private String image;


}
