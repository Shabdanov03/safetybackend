package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safetybackend.sefetybackend.entity.Emergency;

public interface EmergencyRepository extends JpaRepository<Emergency,Long> {
}
