package safetybackend.sefetybackend.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import safetybackend.sefetybackend.dto.request.auth.SignUpRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;
import safetybackend.sefetybackend.dto.response.user.UserUpdateResponse;

@RequestMapping(UserApi.USER_API_PATH)
@Tag(name = "Пользовательский часть", description = UserApi.USER_API_PATH)
public interface UserApi {
    String USER_API_PATH = "/api/user";

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is update user method")
    @PutMapping()
    UserUpdateResponse update(@RequestBody SignUpRequest request);

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is get user by id method")
    @GetMapping()
    UserResponse getUserById();

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is update user method")
    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SimpleResponse saveUserImage(@RequestParam MultipartFile multipartFile);

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "This is get user image method")
    @GetMapping("/{fileName}")
    InputStreamResource getUserImage(@PathVariable String fileName);
}
