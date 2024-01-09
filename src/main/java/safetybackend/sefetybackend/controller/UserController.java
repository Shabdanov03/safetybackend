package safetybackend.sefetybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<UserUpdateResponse> update (@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is get user by id method")
    @GetMapping()
    public UserResponse getUserById() {
        return userService.getUser();
    }

}
