package safetybackend.sefetybackend.service;

import safetybackend.sefetybackend.dto.request.company.CompanyRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;

public interface CompanyService {
    SimpleResponse saveCompany(CompanyRequest request);

}
