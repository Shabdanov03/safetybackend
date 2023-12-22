package safetybackend.sefetybackend.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignInRequest {
    @NotBlank(message = "The email must not be empty.")
    @NotNull(message = "The email must not be empty.")
    @Email(message = "Sorry, the email address you entered is invalid. Please check if it is correct")
    private String email;

    @NotBlank(message = "The password must not be empty.")
    @NotNull(message = "The password must not be empty.")
    private String password;

}
