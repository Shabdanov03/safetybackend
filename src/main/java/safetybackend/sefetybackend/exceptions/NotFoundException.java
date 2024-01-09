package safetybackend.sefetybackend.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {

    }

    public NotFoundException(String massage) {
        super(massage);
    }


}