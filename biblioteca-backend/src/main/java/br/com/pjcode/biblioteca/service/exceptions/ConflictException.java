package br.com.pjcode.biblioteca.service.exceptions;

public class ConflictException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
