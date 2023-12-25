package safetybackend.sefetybackend.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class SimpleResponse {
    private String message;
    private HttpStatus status;
}
