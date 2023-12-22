package safetybackend.sefetybackend.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignUpRequest {
    @NotBlank(message = "The first name must not be empty.")
    @NotNull(message = "The first name must not be empty.")
    private String firstName;

    @NotBlank(message = "The last name must not be empty.")
    @NotNull(message = "The last name must not be empty.")
    private String lastName;

    @NotBlank(message = "The age must not be empty.")
    @NotNull(message = "The age must not be empty.")
    private Byte age;

    @NotBlank(message = "The email must not be empty.")
    @NotNull(message = "The email must not be empty.")
    @Email(message = "Sorry, the email address you entered is invalid. Please check if it is correct")
    private String email;

    @NotBlank(message = "The first phone number must not be empty.")
    @NotNull(message = "The first phone number must not be empty.")
    private String phoneNumber1;
    private String phoneNumber2;

    @NotBlank(message = "The city must not be empty.")
    @NotNull(message = "The city must not be empty.")
    private String city;

    @NotBlank(message = "The address must not be empty.")
    @NotNull(message = "The address must not be empty.")
    private String address;

    @NotBlank(message = "The password must not be empty.")
    @NotNull(message = "The password must not be empty.")
    private String password;
}
