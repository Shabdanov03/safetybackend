package safetybackend.sefetybackend.service;

import safetybackend.sefetybackend.dto.request.auth.ForgotPassword;
import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;

public interface UserService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);

    AuthenticationResponse signIn(SignInRequest authenticationRequest);

    SimpleResponse forgotPassword(ForgotPassword forgotPassword);

    SimpleResponse resetPassword(String code, String newPassword);

    UserResponse updateUser(Long userId, SignUpRequest request);


}
