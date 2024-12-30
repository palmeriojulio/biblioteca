import { AbstractControl, ValidationErrors } from '@angular/forms';


/**
 * Função que cria um validador personalizado para aceitar apenas letras (A-Z, a-z, e o campo pode ser vazio).
 *
 * @param control - Controle do formul rio a ser validado.
 * @returns null se o valor do controle for v lido, ou um objeto de erro se inv lido.
 */
export function apenasLetrasValidator(control: AbstractControl): ValidationErrors | null {

  // Permite valores vazios (caso sejam opcionais)
  if (!control.value || control.value.trim() === '') {
    return null; // se o campo está vazio, não exibe erro
  }

  const regex = /^[a-zA-ZÀ-ú\s]+$/;

  // Verifica se o valor do controle atende à expressão regular
  const valid = regex.test(control.value);

  // Retorna null se válido, ou um objeto de erro se inválido
  return valid ? null : { apenasLetras: true };
}

