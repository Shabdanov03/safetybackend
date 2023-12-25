package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import safetybackend.sefetybackend.entity.UserInfo;

import java.util.Optional;


public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    boolean existsByEmail(String email);

    @Query("select u from UserInfo u where u.resetPasswordCode = ?1")
    Optional<UserInfo> findUserInfoByCode(String code);

    Optional<UserInfo> findUserInfoByUserId(Long userId);
}
