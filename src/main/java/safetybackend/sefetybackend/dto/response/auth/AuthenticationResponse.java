package safetybackend.sefetybackend.dto.response.auth;

import lombok.Builder;
import lombok.Getter;
import safetybackend.sefetybackend.enums.Role;

@Builder
@Getter
public class AuthenticationResponse {
    public String token;
    public String email;
    public Role role;

}
