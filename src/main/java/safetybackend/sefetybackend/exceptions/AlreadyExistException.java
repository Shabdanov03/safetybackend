package safetybackend.sefetybackend.exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException() {

    }

    public AlreadyExistException(String massage) {
        super(massage);
    }
}