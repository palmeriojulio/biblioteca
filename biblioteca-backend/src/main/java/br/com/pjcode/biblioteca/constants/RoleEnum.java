package br.com.pjcode.biblioteca.constants;
/**
 * @author Palmério Júlio
 * Enum referente as regras de perfil.
 */
public enum RoleEnum {
    ADMIN("ADM"),
    USER("USER");

    private String descricao;
    RoleEnum(String descricao) {
        this.descricao= descricao;
    }
}
