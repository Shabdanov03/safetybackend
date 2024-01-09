package safetybackend.sefetybackend.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNeedHelpRequest {
    @NotBlank(message = "The user id must not be empty.")
    @NotNull(message = "The user id must not be empty.")
    private Long userId;
    @NotBlank(message = "The user lat must not be empty.")
    @NotNull(message = "The user lat  must not be empty.")
    private Double userLat;
    @NotBlank(message = "The user long must not be empty.")
    @NotNull(message = "The user long must not be empty.")
    private Double userLong;
}
