package safetybackend.sefetybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import safetybackend.sefetybackend.config.minio.MinioService;

@RestController
@RequestMapping("/api/minio-file")
@RequiredArgsConstructor
@CrossOrigin
public class MinioController {
    private final MinioService minioService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "This is upload image method")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadFile(@RequestParam Long userId, @RequestParam MultipartFile multipartFile) {
        String fileName = "user-" + userId + "-image";
        minioService.saveImage(fileName, multipartFile);
    }
}
