package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safetybackend.sefetybackend.entity.User;

public interface UserInfoRepository extends JpaRepository<User,Long> {

}
