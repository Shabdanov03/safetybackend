package safetybackend.sefetybackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import safetybackend.sefetybackend.api.AuthApi;
import safetybackend.sefetybackend.dto.request.auth.ForgotPassword;
import safetybackend.sefetybackend.dto.request.auth.ResetPasswordRequest;
import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;
import safetybackend.sefetybackend.service.UserService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController implements AuthApi {
    private final UserService userService;


    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        return userService.signIn(signInRequest);
    }

    @Override
    public SimpleResponse forgotPassword(ForgotPassword forgotPassword) {
        return userService.forgotPassword(forgotPassword);
    }

    @Override
    public SimpleResponse resetPassword(String code, ResetPasswordRequest request) {
        return userService.resetPassword(code,request);
    }
}
