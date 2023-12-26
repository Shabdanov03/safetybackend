package safetybackend.sefetybackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class SimpleResponse {
    private String message;
    private HttpStatus status;
}
