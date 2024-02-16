package safetybackend.sefetybackend.service;

import safetybackend.sefetybackend.dto.request.company.CompanyRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.dto.response.user.UserResponse;

import java.util.List;

public interface CompanyService {
    SimpleResponse saveCompany(CompanyRequest request);

    SimpleResponse deleteById(Long companyId);

    List<UserResponse> getAllUsers();
}
