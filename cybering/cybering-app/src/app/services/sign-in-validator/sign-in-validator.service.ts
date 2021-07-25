import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class SignInValidatorService {

  signInForm = this.fb.group({
    emailControl: [''],
    passwordControl: ['']
  });

  constructor(private fb: FormBuilder) { }

  public checkemailexists(): boolean {
    return this.signInForm.controls.emailControl.errors?.required;
  }

  public checkemailstatus(): boolean {
    return this.signInForm.controls.emailControl.errors?.email;
  }

  public checkemail(): boolean {
    return this.signInForm.controls.emailControl.valid;
  }

  public checkpasswordexists(): boolean {
    return this.signInForm.controls.passwordControl.errors?.required;
  }

  public initValidationForm(): FormGroup {
    this.signInForm = this.fb.group({
      emailControl: ['', Validators.required, Validators.email],
      passwordControl: ['', Validators.required]
    });
    return this.signInForm;
  }

  public initEmptyForm(): FormGroup {
    this.signInForm = this.fb.group({
      emailControl: [''],
      passwordControl: ['']
    });
    return this.signInForm;
  }

  public isEmpty(signInForm: FormGroup): boolean {
    if (this.signInForm.controls.emailControl.value === ''
      && this.signInForm.controls.passwordControl.value === '') {
      return true;
    }
    return false;
  }
}
