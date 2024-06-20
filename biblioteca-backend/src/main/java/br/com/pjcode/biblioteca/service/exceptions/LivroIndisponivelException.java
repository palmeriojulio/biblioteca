package br.com.pjcode.biblioteca.service.exceptions;

public class LivroIndisponivelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LivroIndisponivelException(String msg) {
        super(msg);
    }

    public LivroIndisponivelException(String msg, Throwable cause){
        super(msg,cause);
    }
}
