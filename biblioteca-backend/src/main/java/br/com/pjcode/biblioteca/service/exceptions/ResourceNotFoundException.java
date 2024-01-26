package br.com.pjcode.biblioteca.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
    public ResourceNotFoundException(String msg, Throwable cause){
        super(msg,cause);
    }
}
