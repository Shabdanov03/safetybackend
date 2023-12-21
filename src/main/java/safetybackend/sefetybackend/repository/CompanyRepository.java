package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safetybackend.sefetybackend.entity.Company;

public interface CompanyRepository extends JpaRepository< Company,Long> {
}
