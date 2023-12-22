package safetybackend.sefetybackend.service;

import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;

public interface UserService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);

    AuthenticationResponse signIn(SignInRequest authenticationRequest);
}
