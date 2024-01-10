package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safetybackend.sefetybackend.entity.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findAddressByUserId(Long userId);
}
