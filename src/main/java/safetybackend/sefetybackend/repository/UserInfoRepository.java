package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safetybackend.sefetybackend.entity.UserInfo;


public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
boolean existsByEmail(String email);
}
