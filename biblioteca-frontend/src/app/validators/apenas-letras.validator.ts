import { AbstractControl, ValidatorFn } from '@angular/forms';

// Validador personalizado para aceitar apenas letras
export function apenasLetrasValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const isValid = /^[a-zA-Z]+$/.test(control.value);
    return isValid ? null : { apenasLetras: { value: control.value } };
  };
}
