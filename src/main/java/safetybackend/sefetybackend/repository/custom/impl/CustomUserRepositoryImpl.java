package safetybackend.sefetybackend.repository.custom.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import safetybackend.sefetybackend.dto.response.user.UserResponse;
import safetybackend.sefetybackend.enums.Role;
import safetybackend.sefetybackend.enums.UserStatus;
import safetybackend.sefetybackend.exceptions.NotFoundException;
import safetybackend.sefetybackend.repository.custom.CustomUserRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<UserResponse> getUserById(Long userId) {
        String query = """
                SELECT
                u.id AS id,
                CONCAT(u.first_name,' ',u.last_name) AS full_name,
                u.date_of_birth AS date_of_birth,
                u.image AS image,
                u.user_status AS userStatus,
                ui.email AS email,
                ui.password AS password,
                ui.role AS role,
                a.sim1 AS phone_number1,
                a.sim2 AS phone_number2,
                a.city AS city,
                a.address AS address,
                c.name AS company_name
                FROM users u
                LEFT JOIN user_infos ui ON u.id = ui.user_id
                LEFT JOIN addresses a ON u.id = a.user_id
                LEFT JOIN companies c ON u.company_id = c.id
                WHERE ui.role = 'USER' and u.id = ?
                """;
        UserResponse userResponse = jdbcTemplate.query(query, (resultSet, i) ->
                        UserResponse.builder()
                                .id(resultSet.getLong("id"))
                                .fullName(resultSet.getString("full_name"))
                                .dateOfBirth(resultSet.getDate("date_of_birth").toLocalDate())
                                .image(resultSet.getString("image"))
                                .userStatus(UserStatus.valueOf(resultSet.getString("userStatus")))
                                .email(resultSet.getString("email"))
                                .password(resultSet.getString("password"))
                                .role(Role.valueOf(resultSet.getString("role")))
                                .phoneNumber1(resultSet.getString("phone_number1"))
                                .phoneNumber2(resultSet.getString("phone_number2"))
                                .city(resultSet.getString("city"))
                                .address(resultSet.getString("address"))
                                .companyName(resultSet.getString("company_name"))
                                .build(), userId).stream().findAny()
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s was not found", userId)));

        return Optional.of(userResponse);
    }
}
