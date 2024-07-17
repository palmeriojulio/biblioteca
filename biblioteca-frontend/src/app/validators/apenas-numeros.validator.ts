import { AbstractControl, ValidationErrors } from '@angular/forms';

// Função que cria um validador personalizado para aceitar apenas números
export function apenasNumerosValidator(control: AbstractControl): ValidationErrors | null {
  // Define a expressão regular que permite apenas dígitos (0-9)
  const regex = /^[0-9]*$/;

  // Verifica se o valor do controle atende à expressão regular
  const valid = regex.test(control.value);

  // Retorna null se válido, ou um objeto de erro se inválido
  return valid ? null : { apenasNumeros: true };
}
