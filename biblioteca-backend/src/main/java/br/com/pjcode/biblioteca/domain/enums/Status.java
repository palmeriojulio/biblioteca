package br.com.pjcode.biblioteca.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ENTREGUE,
    ATRASADO,
    EMPRESTADO
}
