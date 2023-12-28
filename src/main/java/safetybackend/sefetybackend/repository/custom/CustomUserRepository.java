package safetybackend.sefetybackend.repository.custom;

import safetybackend.sefetybackend.dto.response.user.UserResponse;

import java.util.Optional;

public interface CustomUserRepository {

    Optional<UserResponse> getUserById(Long userId);
}
