package safetybackend.sefetybackend.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import safetybackend.sefetybackend.dto.request.company.CompanyRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;

@RequestMapping(CompanyApi.COMPANY_API_PATH)
@Tag(name = "Компания часть", description = CompanyApi.COMPANY_API_PATH)
public interface CompanyApi {
    String COMPANY_API_PATH = "/api/company";

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This is save test method")
    @PostMapping(value = "/save")
    SimpleResponse save(@RequestBody @Valid CompanyRequest companyRequest);

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This is delete company with id method")
    @DeleteMapping("/{id}")
    SimpleResponse delete(@PathVariable Long id);
}
