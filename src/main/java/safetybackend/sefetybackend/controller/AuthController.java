package safetybackend.sefetybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @PostMapping(value = "/sign-up",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest,
                                                         @RequestPart("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(userService.signUp(signUpRequest,multipartFile));
    }

    @Operation(summary = "This is sign-in method")
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseEntity.ok(userService.signIn(signInRequest));
    }

    @Operation(summary = "This is forgot-password method")
    @PostMapping("/forgot-password")
    public ResponseEntity<SimpleResponse> processForgotPasswordForm(@RequestBody @Valid ForgotPassword forgotPassword) {
        return ResponseEntity.ok(userService.forgotPassword(forgotPassword));
    }

    @Operation(summary = "This is reset-password method")
    @PostMapping("/reset-password")
    public ResponseEntity<SimpleResponse> resetPassword(@RequestParam String code, @RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(userService.resetPassword(code, request.getNewPassword()));
    }
}
