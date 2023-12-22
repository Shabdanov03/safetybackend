package safetybackend.sefetybackend.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class ResetPasswordRequest {
    @NotBlank(message = "The password must not be empty.")
    @NotNull(message = "The password must not be empty.")
    public String newPassword;
}
