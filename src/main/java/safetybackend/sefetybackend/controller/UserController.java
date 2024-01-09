package safetybackend.sefetybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;
import safetybackend.sefetybackend.dto.response.user.UserUpdateResponse;
import safetybackend.sefetybackend.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is update user method")
    @PutMapping()
    public ResponseEntity<UserUpdateResponse> update(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is get user by id method")
    @GetMapping()
    public UserResponse getUserById() {
        return userService.getUser();
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is update user method")
    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SimpleResponse saveUserImage(@RequestParam MultipartFile multipartFile){
        return userService.saveUserImage(multipartFile);
    }
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is get user image method")
    @GetMapping("/{fileName}")
    public ResponseEntity<InputStreamResource> getUserImage(@PathVariable String fileName){
        return userService.getUserImage(fileName);
    }
}
