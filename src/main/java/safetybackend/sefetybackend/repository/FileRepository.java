package safetybackend.sefetybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safetybackend.sefetybackend.entity.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findFileByUserIdAndFileUrl(Long userId, String fileUrl);
}
