package br.com.pjcode.biblioteca.resource.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {

    private Instant timestemp;
    private Integer status;
    private String error;
    private String messager;
    private String path;
}
