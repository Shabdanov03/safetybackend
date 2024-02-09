package safetybackend.sefetybackend.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import safetybackend.sefetybackend.dto.request.auth.ForgotPassword;
import safetybackend.sefetybackend.dto.request.auth.ResetPasswordRequest;
import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;

@RequestMapping(AuthApi.AUTH_API_PATH)
@Tag(name = "Аутентификация", description = AuthApi.AUTH_API_PATH)
public interface AuthApi {
    String AUTH_API_PATH = "/api/auth";

    @Operation(summary = "This is sign-up method")
    @PostMapping(value = "/signUp")
    AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest);

    @Operation(summary = "This is sign-in method")
    @PostMapping("/signIn")
    AuthenticationResponse signIn(@RequestBody @Valid SignInRequest signInRequest);

    @Operation(summary = "This is forgot-password method")
    @PostMapping("/forgotPassword")
    SimpleResponse forgotPassword(@RequestBody @Valid ForgotPassword forgotPassword);

    @Operation(summary = "This is reset-password method")
    @PostMapping("/resetPassword")
    SimpleResponse resetPassword(@RequestParam String code, @RequestBody @Valid ResetPasswordRequest request);
}
