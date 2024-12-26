import { AbstractControl, ValidationErrors } from '@angular/forms';


/**
 * Função que cria um validador personalizado para aceitar apenas letras (a-z, A-Z) e espaços.
 *
 * @param control - Controle do formulário a ser validado.
 * @returns null se o valor do controle for válido, ou um objeto de erro se inválido.
 */
export function apenasLetrasValidator(control: AbstractControl): ValidationErrors | null {
  const regex = /^[a-zA-ZÀ-ú\s]+$/;

  // Verifica se o valor do controle atende à expressão regular
  const valid = regex.test(control.value);

  // Retorna null se válido, ou um objeto de erro se inválido
  return valid ? null : { apenasLetras: true };
}

