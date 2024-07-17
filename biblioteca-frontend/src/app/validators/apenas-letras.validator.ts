import { AbstractControl, ValidationErrors } from '@angular/forms';

// Função que cria um validador personalizado para aceitar apenas números
export function apenasLetrasValidator(control: AbstractControl): ValidationErrors | null {
  // Define a expressão regular que permite apenas letras (a-z, A-Z) e espaços
  const regex = /^[a-zA-Z\s]*$/;

  // Verifica se o valor do controle atende à expressão regular
  const valid = regex.test(control.value);

  // Retorna null se válido, ou um objeto de erro se inválido
  return valid ? null : { apenasLetras: true };
}
