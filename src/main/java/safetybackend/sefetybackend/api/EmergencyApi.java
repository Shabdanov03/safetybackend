package safetybackend.sefetybackend.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import safetybackend.sefetybackend.dto.request.user.UserNeedHelpRequest;
import safetybackend.sefetybackend.dto.request.user.UserSuspendHelpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;

@RequestMapping(EmergencyApi.EMERGENCY_API_PATH)
@Tag(name = "Помощь  часть", description = EmergencyApi.EMERGENCY_API_PATH)
public interface EmergencyApi {
    String EMERGENCY_API_PATH = "/api/emergency";

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "This is need emergency help method")
    @PostMapping("/need-emergency")
    SimpleResponse needEmergencyHelpAndChangeStatus(@RequestBody @Valid UserNeedHelpRequest userNeedHelpRequest);

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "This is suspend emergency help method")
    @PostMapping("/suspend-emergency")
    SimpleResponse suspendEmergencyHelp(@RequestBody @Valid UserSuspendHelpRequest suspendHelpRequest);
}
