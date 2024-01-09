package safetybackend.sefetybackend.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSuspendHelpRequest {
    @NotNull(message = "The user id must not be empty.")
    private Long userId;
    @NotNull(message = "The description must not be empty.")
    private String description;
}
