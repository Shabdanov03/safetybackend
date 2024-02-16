package safetybackend.sefetybackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import safetybackend.sefetybackend.api.CompanyApi;
import safetybackend.sefetybackend.dto.request.company.CompanyRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;
import safetybackend.sefetybackend.service.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CompanyController implements CompanyApi {
    private final CompanyService companyService;


    @Override
    public SimpleResponse save(CompanyRequest companyRequest) {
        return companyService.saveCompany(companyRequest);
    }

    @Override
    public SimpleResponse delete(Long id) {
        return companyService.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return companyService.getAllUsers();
    }
}
