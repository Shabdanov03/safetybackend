package safetybackend.sefetybackend.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {

    }

    public BadRequestException(String massage) {
        super(massage);
    }
}