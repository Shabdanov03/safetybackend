package safetybackend.sefetybackend.dto.request.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompanyRequest {
    @NotBlank(message = "The company name must not be empty.")
    @NotNull(message = "The company name must not be empty.")
    private String companyName;

    @NotBlank(message = "The phone number  must not be empty.")
    @NotNull(message = "The phone number must not be empty.")
    private String phoneNumber;

    @NotBlank(message = "The email must not be empty.")
    @NotNull(message = "The email must not be empty.")
    @Email(message = "Sorry, the email address you entered is invalid. Please check if it is correct")
    private String email;

    @NotBlank(message = "The password must not be empty.")
    @NotNull(message = "The password must not be empty.")
    private String password;

}
