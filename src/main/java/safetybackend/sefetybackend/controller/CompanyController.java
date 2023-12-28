package safetybackend.sefetybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import safetybackend.sefetybackend.dto.request.company.CompanyRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.service.CompanyService;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@CrossOrigin
public class CompanyController {
    private final CompanyService companyService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This is save test method")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid CompanyRequest companyRequest) {
        return companyService.saveCompany(companyRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This is delete company with id method")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return companyService.deleteById(id);
    }
}
