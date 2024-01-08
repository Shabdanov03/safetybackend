package safetybackend.sefetybackend.config.minio;

import io.minio.PutObjectArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import safetybackend.sefetybackend.entity.File;
import safetybackend.sefetybackend.entity.User;
import safetybackend.sefetybackend.exceptions.BadRequestException;
import safetybackend.sefetybackend.exceptions.NotFoundException;
import safetybackend.sefetybackend.repository.FileRepository;
import safetybackend.sefetybackend.repository.UserRepository;

import java.io.InputStream;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    @Value("${minio.bucket}")
    private String bucketName;

    public void saveImage(String fileName, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build()


            );
         //   User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User with id: %s not found !"));
            File file1 = new File();
            file1.setFileUrl(fileName);
          //  file1.setUser(user);
            fileRepository.save(file1);
            log.info("Image saved successfully: {}", fileName);
        } catch (Exception e) {
            log.error("Error saving image to MinIO: {}", e.getMessage(), e);
            throw new BadRequestException("Error saving image to MinIO:");
        }
    }

}
