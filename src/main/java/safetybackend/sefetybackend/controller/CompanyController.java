package safetybackend.sefetybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,value = "/save")
    public SimpleResponse save(@RequestBody @Valid CompanyRequest companyRequest,@RequestParam MultipartFile multipartFile) {
        return companyService.saveCompany(companyRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This is delete company with id method")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return companyService.deleteById(id);
    }

}
