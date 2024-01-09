package safetybackend.sefetybackend.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNeedHelpRequest {
    @NotNull(message = "The user id must not be empty.")
    private Long userId;
    @NotNull(message = "The user lat  must not be empty.")
    private Double userLat;
    @NotNull(message = "The user long must not be empty.")
    private Double userLong;
}
