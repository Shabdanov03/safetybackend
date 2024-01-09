package safetybackend.sefetybackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/need-emergency-help-and-change-status")
    public SimpleResponse needEmergencyHelpAndChangeStatus(@RequestBody @Valid UserNeedHelpRequest userNeedHelpRequest){
        return userService.needEmergencyHelpAndChangeUserStatus(userNeedHelpRequest);
    }

    @PostMapping("/suspend-emergency-help")
    public SimpleResponse suspendEmergencyHelp(@RequestBody @Valid UserSuspendHelpRequest suspendHelpRequest){
        return userService.suspendEmergencyHelp(suspendHelpRequest);
    }
}
