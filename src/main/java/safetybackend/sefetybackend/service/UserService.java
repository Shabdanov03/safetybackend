package safetybackend.sefetybackend.service;

import org.springframework.web.multipart.MultipartFile;
import safetybackend.sefetybackend.dto.request.auth.ForgotPassword;
import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;
import safetybackend.sefetybackend.dto.response.user.UserUpdateResponse;

public interface UserService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest, MultipartFile multipartFile);

    AuthenticationResponse signIn(SignInRequest authenticationRequest);

    SimpleResponse forgotPassword(ForgotPassword forgotPassword);

    SimpleResponse resetPassword(String code, String newPassword);

    UserUpdateResponse updateUser(Long userId, SignUpRequest request);

    SimpleResponse deleteById(Long userId);

    UserResponse getUser();
}
