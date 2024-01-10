package safetybackend.sefetybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import safetybackend.sefetybackend.dto.request.user.UserNeedHelpRequest;
import safetybackend.sefetybackend.dto.request.user.UserSuspendHelpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.service.UserService;

@RestController
@RequestMapping("/api/emergency")
@RequiredArgsConstructor
@CrossOrigin
public class EmergencyController {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "This is need emergency help method")
    @PostMapping("/need-emergency")
    public SimpleResponse needEmergencyHelpAndChangeStatus(@RequestBody @Valid UserNeedHelpRequest userNeedHelpRequest) {
        return userService.needEmergencyHelpAndChangeUserStatus(userNeedHelpRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "This is suspend emergency help method")
    @PostMapping("/suspend-emergency")
    public SimpleResponse suspendEmergencyHelp(@RequestBody @Valid UserSuspendHelpRequest suspendHelpRequest) {
        return userService.suspendEmergencyHelp(suspendHelpRequest);
    }
}
