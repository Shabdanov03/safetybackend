package safetybackend.sefetybackend.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSuspendHelpRequest {
    @NotBlank(message = "The user id must not be empty.")
    @NotNull(message = "The description must not be empty.")
    private Long userId;
    private String description;
}
