import { AbstractControl, ValidationErrors } from '@angular/forms';

/**
 * Função que cria um validador personalizado para aceitar apenas números (0-9, . e -).
 *
 * @param control - Controle do formulário a ser validado.
 * @returns null se o valor do controle for válido, ou um objeto de erro se inválido.
 */
export function apenasNumerosValidator(control: AbstractControl): ValidationErrors | null {

  const valor = control.value;

  // Permite valores vazios (caso sejam opcionais)
  if (!valor) {
    return null;
  }

  // Define a expressão regular que permite apenas dígitos (0-9, . e -)
  const regex =  /^[0-9.-]*$/;

  // Verifica se o valor do controle atende à expressão regular
  const valid = regex.test(control.value);

  // Retorna null se válido, ou um objeto de erro se inválido
  return valid ? null : { apenasNumeros: true };
}
