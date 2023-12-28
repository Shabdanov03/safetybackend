package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import safetybackend.sefetybackend.entity.User;
import safetybackend.sefetybackend.entity.UserInfo;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(" select u from UserInfo u where u.email = ?1")
    Optional<UserInfo> findUserInfoByEmail(String email);

    boolean existsByUserInfoEmail(String email);


}
