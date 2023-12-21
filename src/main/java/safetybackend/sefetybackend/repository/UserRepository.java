package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safetybackend.sefetybackend.entity.User;

public interface UserRepository extends JpaRepository< User,Long> {

}
