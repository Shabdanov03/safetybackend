package safetybackend.sefetybackend.dto.request.user;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNeedHelpRequest {
    private Long userId;
    private String userLocationName;
    private Double userLat;
    private Double userLong;
}
