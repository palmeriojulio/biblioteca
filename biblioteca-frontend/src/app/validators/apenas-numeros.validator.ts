import { AbstractControl, ValidatorFn } from '@angular/forms';

// Validador personalizado para aceitar apenas números
export function apenasNumerosValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const isValid = /^\d+$/.test(control.value);
    return isValid ? null : { apenasNumeros: { value: control.value } };
  };
}
