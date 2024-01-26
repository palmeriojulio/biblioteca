package br.com.pjcode.biblioteca.service.exceptions;

public class InternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InternalServerErrorException(String msg) {
        super(msg);
    }
    public InternalServerErrorException(String msg, Throwable cause){
        super(msg,cause);
    }
}
