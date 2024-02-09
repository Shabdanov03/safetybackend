package safetybackend.sefetybackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import safetybackend.sefetybackend.api.EmergencyApi;
import safetybackend.sefetybackend.dto.request.user.UserNeedHelpRequest;
import safetybackend.sefetybackend.dto.request.user.UserSuspendHelpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.service.UserService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class EmergencyController implements EmergencyApi {
    private final UserService userService;


    @Override
    public SimpleResponse needEmergencyHelpAndChangeStatus(UserNeedHelpRequest userNeedHelpRequest) {
        return userService.needEmergencyHelpAndChangeUserStatus(userNeedHelpRequest);
    }

    @Override
    public SimpleResponse suspendEmergencyHelp(UserSuspendHelpRequest suspendHelpRequest) {
        return userService.suspendEmergencyHelp(suspendHelpRequest);
    }
}
