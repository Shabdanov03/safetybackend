package safetybackend.sefetybackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import safetybackend.sefetybackend.dto.request.company.CompanyRequest;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.entity.Company;
import safetybackend.sefetybackend.entity.UserInfo;
import safetybackend.sefetybackend.enums.Role;
import safetybackend.sefetybackend.exceptions.AlreadyExistException;
import safetybackend.sefetybackend.repository.CompanyRepository;
import safetybackend.sefetybackend.service.CompanyService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SimpleResponse saveCompany(CompanyRequest request) {
        log.info("Save company ");
        if (companyRepository.existsByUserInfoEmail(request.getEmail())) {
            throw new AlreadyExistException("Sorry, this email is already registered. Please try a different email or login to your existing account");
        }
        UserInfo newUserInfo = UserInfo.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.COMPANY)
                .build();

        Company newCompany = Company.builder()
                .name(request.getCompanyName())
                .phoneNumber(request.getPhoneNumber())
                .userInfo(newUserInfo)
                .build();
        companyRepository.save(newCompany);

        log.info("Company save successful");

        return SimpleResponse.builder()
                .message(String.format("Company with %s name successfully saved", request.getCompanyName()))
                .build();
    }

}
