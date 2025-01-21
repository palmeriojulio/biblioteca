package br.com.pjcode.biblioteca.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeitorFaixaEtariaDto {

    private long faixa0_12;
    private long faixa13_18;
    private long faixa19_25;
    private long faixa26_40;
    private long faixa41;
    public LeitorFaixaEtariaDto(long faixa0_12, long faixa13_18, long faixa19_25, long faixa26_40, long faixa41) {
        this.faixa0_12 = faixa0_12;
        this.faixa13_18 = faixa13_18;
        this.faixa19_25 = faixa19_25;
        this.faixa26_40 = faixa26_40;
        this.faixa41 = faixa41;
    }
}
