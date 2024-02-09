package safetybackend.sefetybackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import safetybackend.sefetybackend.api.UserApi;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;
import safetybackend.sefetybackend.dto.response.user.UserUpdateResponse;
import safetybackend.sefetybackend.service.UserService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public UserUpdateResponse update(SignUpRequest request) {
        return userService.updateUser(request);
    }

    @Override
    public UserResponse getUserById() {
        return userService.getUser();
    }

    @Override
    public SimpleResponse saveUserImage(MultipartFile multipartFile) {
        return userService.saveUserImage(multipartFile);
    }

    @Override
    public InputStreamResource getUserImage(String fileName) {
        return userService.getUserImage(fileName);
    }
}
