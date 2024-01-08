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

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "This is update user method")
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> update(@PathVariable @Valid Long id, @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This is delete user method")
    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponse> delete(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is get user by id method")
    @GetMapping()
    public UserResponse getUserById() {
        return userService.getUser();
    }

}
