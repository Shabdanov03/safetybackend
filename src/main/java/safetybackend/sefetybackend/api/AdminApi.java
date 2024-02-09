package safetybackend.sefetybackend.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import safetybackend.sefetybackend.dto.response.SimpleResponse;

@RequestMapping(AdminApi.ADMIN_API_PATH)
@Tag(name = "Админ часть ", description = AdminApi.ADMIN_API_PATH)
public interface AdminApi {
    String ADMIN_API_PATH = "/api/admin";

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This is delete user method")
    @DeleteMapping("/{id}")
    SimpleResponse delete(@PathVariable @Valid Long id);

}
