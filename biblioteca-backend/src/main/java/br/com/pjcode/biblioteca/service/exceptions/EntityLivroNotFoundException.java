package br.com.pjcode.biblioteca.service.exceptions;

public class EntityLivroNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public EntityLivroNotFoundException(String msg) {
        super(msg);
    }
}
