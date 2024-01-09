package safetybackend.sefetybackend.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import safetybackend.sefetybackend.config.jwtConfig.JwtService;
import safetybackend.sefetybackend.config.minio.MinioService;
import safetybackend.sefetybackend.dto.request.auth.ForgotPassword;
import safetybackend.sefetybackend.dto.request.auth.SignInRequest;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.request.user.UserNeedHelpRequest;
import safetybackend.sefetybackend.dto.request.user.UserSuspendHelpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.auth.AuthenticationResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;
import safetybackend.sefetybackend.dto.response.user.UserUpdateResponse;
import safetybackend.sefetybackend.entity.*;
import safetybackend.sefetybackend.enums.Role;
import safetybackend.sefetybackend.enums.UserStatus;
import safetybackend.sefetybackend.exceptions.AlreadyExistException;
import safetybackend.sefetybackend.exceptions.BadRequestException;
import safetybackend.sefetybackend.exceptions.NotFoundException;
import safetybackend.sefetybackend.repository.*;
import safetybackend.sefetybackend.repository.custom.CustomUserRepository;
import safetybackend.sefetybackend.service.EmailService;
import safetybackend.sefetybackend.service.UserService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final CompanyRepository companyRepository;
    private final CustomUserRepository customUserRepository;
    private final FileRepository fileRepository;
    private final MinioService minioService;
    private final EmergencyRepository emergencyRepository;
    private static final int CODE_LENGTH = 6;


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
        Company company = companyRepository.findById(1L).orElseThrow(() -> new NotFoundException(
                String.format("Company with id : %s not found !", 1L)));

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
        newUser.setDateOfBirth(signUpRequest.getDateOfBirth());
        newUser.setIsActive(false);
        newUser.setUserStatus(UserStatus.OK);
        newUser.setUserInfo(newUserInfo);
        newUser.setAddress(newAddress);
        newUserInfo.setUser(newUser);
        newAddress.setUser(newUser);
        company.setUsers(List.of(newUser));
        newUser.setCompany(company);
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
        UserInfo user = userRepository.findUserInfoByEmail(signInRequest.getEmail())
                .orElseThrow(() -> {
                    log.error("User with email: %s not found".formatted(signInRequest.getEmail()));
                    return new NotFoundException(
                            "User with email: %s not found".formatted(signInRequest.getEmail()));
                });
        if (signInRequest.getPassword().isBlank()) {
            log.error("Password is blank!");
            throw new BadRequestException("Password is blank!");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            log.error("Wrong password");
            throw new BadRequestException("Wrong password!");
        }
        log.info("Generation of a token for a registered user");
        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public SimpleResponse forgotPassword(ForgotPassword forgotPassword) {
        log.info("Initiating password reset");
        UserInfo userInfo = userRepository.findUserInfoByEmail(forgotPassword.getEmail())
                .orElseThrow(() -> new NotFoundException("User was not found"));
        try {
            int code = generateRandomCode();
            userInfo.setResetPasswordCode(code);
            userInfoRepository.save(userInfo);
            log.info("Reset password code saved for user: {}", userInfo.getUsername());

            String subject = "Password Reset Request";

            Context context = new Context();
            context.setVariable("title", "Password Reset");
            context.setVariable("message", "Мы получили запрос на сброс вашего пароля. Введите следующий код:");
            context.setVariable("code", String.valueOf(code));      // TODO int format to String
            context.setVariable("codeTitle", "Reset Password Code");

            String htmlContent = templateEngine.process("reset-password-template.html", context);
            emailService.sendEmail(forgotPassword.getEmail(), subject, htmlContent);
            log.info("Password reset code sent via email");

            return SimpleResponse.builder()
                    .message("The password reset code was sent to your email. Please check your email.")
                    .build();
        } catch (MessagingException e) {
            log.error("Error sending password reset code email", e);
            return SimpleResponse.builder()
                    .message("An error occurred while sending the password reset code.")
                    .build();
        }
    }

    // TODO Generate random code for forgot password
    private int generateRandomCode() {
        SecureRandom random = new SecureRandom();
        int code = 0;

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomDigit = random.nextInt(10);
            code = code * 10 + randomDigit;
        }

        return code;
    }


    @Override
    public SimpleResponse resetPassword(String code, String newPassword) {
        log.info("Resetting password ");
        try {
            UserInfo userInfo = userInfoRepository.findUserInfoByCode(code)
                    .orElseThrow(() -> new NotFoundException("User was not found"));

            log.info("Found user: {}", userInfo);

            String encodedPassword = passwordEncoder.encode(newPassword);
            userInfo.setPassword(encodedPassword);
            userInfo.setResetPasswordCode(0);

            userInfoRepository.save(userInfo);

            log.info("Password reset successful for user: {}", userInfo.getUsername());
            return SimpleResponse.builder().message("User password changed successfully!").build();
        } catch (NotFoundException e) {
            log.error("Error resetting password: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error resetting password", e);
            return SimpleResponse.builder().message("An error occurred while resetting the password.").build();
        }
    }

    @Override
    public SimpleResponse saveUserImage(final MultipartFile multipartFile) {
        try {
            UserInfo userInfo = jwtService.getAuthenticationUser();
            String originalFilename = System.currentTimeMillis() + multipartFile.getOriginalFilename();

            minioService.saveImage(originalFilename, multipartFile);

            log.info("User image saved successfully. User: {}, Filename: {}", userInfo.getUsername(), originalFilename);

            File newFile = new File();
            newFile.setFileUrl(originalFilename);
            newFile.setUser(userInfo.getUser());
            fileRepository.save(newFile);
            return SimpleResponse.builder()
                    .message("User image successfully saved!")
                    .status(HttpStatus.CREATED)
                    .build();
        } catch (BadRequestException e) {
            log.error("Error saving user image", e);
            return SimpleResponse.builder()
                    .message("Error saving user image")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    public ResponseEntity<InputStreamResource> getUserImage(String fileName) {
            UserInfo tokenUserInfo = jwtService.getAuthenticationUser();
            File file = fileRepository.findFileByUserIdAndFileUrl(tokenUserInfo.getUser().getId(), fileName)
                    .orElseThrow(() -> new NotFoundException(String.format("File with name '%s' not found for user with id: %s", fileName, tokenUserInfo.getUser().getId())));

            log.info("Find file by user id and filename successfully");

            return minioService.getMinioImage(file.getFileUrl());
    }



    @Override
    public UserUpdateResponse updateUser(SignUpRequest request) {
        log.info("User update");
        UserInfo tokenUserInfo = jwtService.getAuthenticationUser();
        User user = userRepository.findById(tokenUserInfo.getUser().getId()).orElseThrow(() ->
                new NotFoundException(String.format("User with id: %s not found !", tokenUserInfo.getUser().getId())));
        log.info("find user by id successful");

        UserInfo userInfo = userInfoRepository.findUserInfoByUserId(tokenUserInfo.getUser().getId()).orElseThrow(() ->
                new NotFoundException(String.format("User info with id: %s not found !", tokenUserInfo.getUser().getId())));
        log.info("find user info by user id successful");

        Address address = addressRepository.findAddressByUserId(tokenUserInfo.getUser().getId()).orElseThrow(() ->
                new NotFoundException(String.format("Address with id: %s not found !", tokenUserInfo.getUser().getId())));
        log.info("find address by user id successful");

        File file = fileRepository.findFileByUserId(tokenUserInfo.getUser().getId()).orElseThrow(() ->
                new NotFoundException(String.format("File with id: %s not found !", tokenUserInfo.getUser().getId())));
        log.info("find file by user id successful");

        //TODO Update user
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }

        // TODO Update user info
        if (request.getEmail() != null) {
            userInfo.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            userInfo.setPassword(request.getPassword());
        }
        // TODO Update user address
        if (request.getPhoneNumber1() != null) {
            address.setSim1(request.getPhoneNumber1());
        }
        if ((request.getPhoneNumber2() != null)) {
            address.setSim2(request.getPhoneNumber2());
        }
        if (request.getCity() != null) {
            address.setCity(request.getCity());
        }
        if (request.getAddress() != null) {
            address.setAddress(request.getAddress());
        }
        user.setAddress(address);
        user.setUserInfo(userInfo);
        userRepository.save(user);

        log.info("User update successful");

        return UserUpdateResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber1(user.getAddress().getSim1())
                .phoneNumber2(user.getAddress().getSim2())
                .city(user.getAddress().getCity())
                .address(user.getAddress().getAddress())
                .email(user.getUserInfo().getEmail())
                .password(user.getUserInfo().getPassword())
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse deleteById(Long userId) {
        log.info("Deleting user with id: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            String errorMessage = String.format("User with id '%d' not found", userId);
            log.error(errorMessage);
            return new NotFoundException(errorMessage);
        });
        userRepository.delete(user);

        log.info("User with id '{}' successfully deleted", userId);
        return SimpleResponse.builder()
                .message(String.format("User with id '%d' successfully deleted", userId))
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public UserResponse getUser() {
        log.info("Getting user by id ");
        UserInfo userInfo = jwtService.getAuthenticationUser();

        return customUserRepository.getUserById(userInfo.getUser().getId()).orElseThrow(
                () -> {
                    String errorMessage = String.format("User with id '%s' was not found", userInfo.getUser().getId());
                    log.error(errorMessage);
                    return new NotFoundException(errorMessage);
                }
        );
    }

    @Override
    public SimpleResponse needEmergencyHelpAndChangeUserStatus(UserNeedHelpRequest needHelpRequest) {
        User user = userRepository.findById(needHelpRequest.getUserId()).orElseThrow(() -> {
            String errorMessage = String.format("User with id '%d' not found", needHelpRequest.getUserId());
            log.error(errorMessage);
            return new NotFoundException(errorMessage);
        });
        log.info("Find user successfully");
        user.setUserStatus(UserStatus.NEED_HELP);
        log.info("Change user status successfully");

        Emergency emergency = new Emergency();
        emergency.setUserLong(needHelpRequest.getUserLong());
        emergency.setUserLat(needHelpRequest.getUserLat());
        user.setEmergency(emergency);
        emergency.setUsers(new ArrayList<>(List.of(user)));
        emergencyRepository.save(emergency);
        log.info("Emergency saved successful!");
        userRepository.save(user);
        log.info("User successfully saved !");
        return SimpleResponse.builder()
                .message("Need emergency help and change user status successful")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse suspendEmergencyHelp(UserSuspendHelpRequest suspendHelpRequest) {
        User user = userRepository.findById(suspendHelpRequest.getUserId()).orElseThrow(() -> {
            String errorMessage = String.format("User with id '%d' not found", suspendHelpRequest.getUserId());
            log.error(errorMessage);
            return new NotFoundException(errorMessage);
        });
        log.info("Find user successfully");
        user.setUserStatus(UserStatus.OK);
        log.info("Change user status successfully");
        userRepository.save(user);
        log.info("User successfully saved !");
        Emergency emergency = user.getEmergency();
        emergency.setDescription(suspendHelpRequest.getDescription());

        emergencyRepository.save(emergency);
        log.info("Emergency saved successful!");
        return SimpleResponse.builder()
                .message("Suspend emergency help successful")
                .status(HttpStatus.OK)
                .build();
    }

    @PostConstruct
    public void addAdmin() {
        log.info("Init method");
        if (!userInfoRepository.existsByEmail(EMAIL)) {
            User user = User.builder()
                    .firstName("Admin")
                    .lastName("Adminov")
                    .build();
            UserInfo userInfo = UserInfo
                    .builder()
                    .email(EMAIL)
                    .password(passwordEncoder.encode(PASSWORD))
                    .role(Role.ADMIN)
                    .user(user)
                    .build();
            userInfoRepository.save(userInfo);
            log.info("Admin saved");
        }
    }

}
