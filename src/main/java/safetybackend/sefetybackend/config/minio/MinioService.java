package safetybackend.sefetybackend.config.minio;

import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import safetybackend.sefetybackend.exceptions.BadRequestException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String BUCKET_NAME;

    public void saveImage(String fileName, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error saving image to MinIO: {}", e.getMessage(), e);
            throw new BadRequestException("Error saving image to MinIO:");
        }
    }


    public ResponseEntity<InputStreamResource> getMinioImage(String filename) {
        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(
                    StatObjectArgs.builder().bucket(BUCKET_NAME).object(filename).build());

            // Получаем "name" из метаданных
            String ogName = statObjectResponse.userMetadata().get("name");

            // Если "name" равно null, используем имя файла
            if (ogName == null) {
                ogName = filename;
            }

            log.info("Original Name from MinIO: {}", ogName);

            InputStream stream = minioClient.getObject(GetObjectArgs
                    .builder()
                    .bucket(BUCKET_NAME)
                    .object(filename)
                    .build());

            HttpHeaders respHeaders = new HttpHeaders();
            respHeaders.setContentDispositionFormData("attachment", ogName);
            respHeaders.setContentType(MediaType.IMAGE_JPEG);

            log.info("Downloading file with name: {}", filename);

            return new ResponseEntity<>(new InputStreamResource(stream), respHeaders, HttpStatus.OK);
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException |
                 XmlParserException | InternalException e) {
            log.error("Error getting image from MinIO: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
