import { Directive } from '@angular/core';
import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appSamePassword]'
})
export class SamePasswordDirective {

  constructor() { }

}

export const samePasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const pw1 = control.get('passwordControl');
  const pw2 = control.get('passwordValidControl');

  return pw1 && pw2 && pw1.value === pw2.value ? null : { samePasswordValidator: true };
};
