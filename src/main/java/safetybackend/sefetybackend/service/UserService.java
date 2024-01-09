package safetybackend.sefetybackend.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import safetybackend.sefetybackend.dto.request.auth.ForgotPassword;
import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.request.user.UserNeedHelpRequest;
import safetybackend.sefetybackend.dto.request.user.UserSuspendHelpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;
import safetybackend.sefetybackend.dto.response.user.UserUpdateResponse;

public interface UserService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);

    AuthenticationResponse signIn(SignInRequest authenticationRequest);

    SimpleResponse forgotPassword(ForgotPassword forgotPassword);

    SimpleResponse resetPassword(String code, String newPassword);

    SimpleResponse saveUserImage(MultipartFile multipartFile);

    ResponseEntity<InputStreamResource> getUserImage(String fileName);
    UserUpdateResponse updateUser(SignUpRequest request);

    SimpleResponse deleteById(Long userId);

    UserResponse getUser();

    SimpleResponse needEmergencyHelpAndChangeUserStatus(UserNeedHelpRequest needHelpRequest);

    SimpleResponse suspendEmergencyHelp(UserSuspendHelpRequest suspendHelpRequest);
}
