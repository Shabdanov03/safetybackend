package safetybackend.sefetybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import safetybackend.sefetybackend.dto.request.auth.ForgotPassword;
import safetybackend.sefetybackend.dto.request.auth.ResetPasswordRequest;
import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;
import safetybackend.sefetybackend.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final UserService userService;

    @Operation(summary = "This is sign-up method")
    @PostMapping(value = "/signUp")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @Operation(summary = "This is sign-in method")
    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseEntity.ok(userService.signIn(signInRequest));
    }

    @Operation(summary = "This is forgot-password method")
    @PostMapping("/forgotPassword")
    public ResponseEntity<SimpleResponse> processForgotPasswordForm(@RequestBody @Valid ForgotPassword forgotPassword) {
        return ResponseEntity.ok(userService.forgotPassword(forgotPassword));
    }

    @Operation(summary = "This is reset-password method")
    @PostMapping("/resetPassword")
    public ResponseEntity<SimpleResponse> resetPassword(@RequestParam String code, @RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(userService.resetPassword(code, request.getNewPassword()));
    }
}
