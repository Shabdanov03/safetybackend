package safetybackend.sefetybackend.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import safetybackend.sefetybackend.config.jwtConfig.JwtService;
import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;
import safetybackend.sefetybackend.entity.Address;
import safetybackend.sefetybackend.entity.User;
import safetybackend.sefetybackend.entity.UserInfo;
import safetybackend.sefetybackend.enums.Role;
import safetybackend.sefetybackend.exceptions.AlreadyExistException;
import safetybackend.sefetybackend.exceptions.NotFoundException;
import safetybackend.sefetybackend.repository.UserInfoRepository;
import safetybackend.sefetybackend.repository.UserRepository;
import safetybackend.sefetybackend.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${spring.admin_password}")
    private String PASSWORD;

    @Value("${spring.admin_email}")
    private String EMAIL;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        log.info("Signing up");
        if (userRepository.existsByUserInfoEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistException("Sorry, this email is already registered. Please try a different email or login to your existing account");
        }
        UserInfo newUserInfo = UserInfo.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();

        Address newAddress = Address.builder()
                .sim1(signUpRequest.getPhoneNumber1())
                .sim2(signUpRequest.getPhoneNumber2())
                .city(signUpRequest.getCity())
                .address(signUpRequest.getAddress())
                .build();

        User newUser = new User();
        newUser.setFirstName(signUpRequest.getFirstName());
        newUser.setLastName(signUpRequest.getLastName());
        newUser.setAge(signUpRequest.getAge());
        newUser.setIsActive(false);
        newUser.setUserInfo(newUserInfo);
        newUser.setAddress(newAddress);
        newUserInfo.setUser(newUser);
        userRepository.save(newUser);

        var jwtToken = jwtService.generateToken(newUserInfo);

        log.info("Sign up successful");

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(newUserInfo.getEmail())
                .role(newUserInfo.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        log.info("User signing in");
        var user = userRepository.findUserInfoByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User was not found."));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);

        log.info("Sign in successful");

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(user.getUsername())
                .role(user.getRole())
                .build();
    }

    @PostConstruct
    public void addAdmin() {
        log.info("Init method");
        if (!userInfoRepository.existsByEmail(EMAIL)) {
            UserInfo user = UserInfo
                    .builder()
                    .email(EMAIL)
                    .password(passwordEncoder.encode(PASSWORD))
                    .role(Role.ADMIN)
                    .build();
            userInfoRepository.save(user);
            log.info("Admin saved");
        }
    }

}
