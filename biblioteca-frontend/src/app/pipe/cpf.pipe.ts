import { Pipe, PipeTransform } from '@angular/core';

// Decorador @Pipe que define o nome do pipe como 'cpf'
@Pipe({
  name: 'cpf'
})
// Classe que implementa a interface PipeTransform
export class CpfPipe implements PipeTransform {

  // Método transform que recebe um valor e retorna uma string formatada como CPF
  transform(value: string | number): string {

    // Converte o valor para string e armazena em valorFormatado
    let valorFormatado = value + '';

    // Formata o valor como CPF:
    valorFormatado = valorFormatado
      // Preenche com zeros à esquerda para garantir que tenha pelo menos 11 caracteres
      .padStart(11, '0')
      // Limita o comprimento da string a 11 caracteres
      .substring(0, 11)
      // Remove qualquer caractere que não seja um dígito
      .replace(/[^0-9]/, '')
      // Adiciona os pontos e traço para formatar o CPF
      .replace(
        /(\d{3})(\d{3})(\d{3})(\d{2})/,
        '$1.$2.$3-$4'
      );

    // Retorna o valor formatado
    return valorFormatado;
  }
}
