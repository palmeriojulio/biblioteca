import { Pipe, PipeTransform } from '@angular/core';

// Decorador @Pipe que define o nome do pipe como 'telefone'
@Pipe({
  name: 'telefone'
})
// Classe que implementa a interface PipeTransform
export class TelefonePipe implements PipeTransform {

  // Método transform que recebe um valor e retorna uma string formatada como telefone
  transform(value: string): string {
    // Utiliza uma expressão regular para adicionar a formatação de telefone
    return value.replace(/(\d{2})?(\d{1})?(\d{4})?(\d{4})/, '($1) $2 $3-$4');
  }
}
