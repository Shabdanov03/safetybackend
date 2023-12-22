package safetybackend.sefetybackend.exceptions;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException() {

    }

    public BadCredentialException(String massage) {
        super(massage);
    }
}