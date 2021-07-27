import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { samePasswordValidator } from 'src/app/validators/same-password.directive';
import { Authorization } from 'src/app/interfaces/authorization';
import { CookieService } from 'ngx-cookie-service';
import { RegisterService } from 'src/service/register-service/register.service';
import { Router } from '@angular/router';
import { RegisterInfo } from 'src/app/interfaces/professional';



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerInfo: RegisterInfo;
  authToken: Authorization;

  registerForm = this.fb.group({
    emailControl: [''],
    firstNameControl: [''],
    lastNameControl: [''],
    passFields: this.fb.group({
      passwordControl: [''],
      passwordValidControl: ['']
    }, { validators: samePasswordValidator })
  });


  constructor(
    private fb: FormBuilder,
    private cookieService: CookieService,
    private registerService: RegisterService,
    private router: Router
  ) { 
    this.authToken = new Authorization();
    this.registerInfo = new RegisterInfo();
  }

  ngOnInit(): void {
  }

  registerSubmit() {
    this.authToken = new Authorization();
    this.cookieService.deleteAll();
    this.registerForm = this.fb.group({
      emailControl: [this.registerForm.controls.emailControl.value, [Validators.required, Validators.email]],
      firstNameControl: [this.registerForm.controls.firstNameControl.value, Validators.required],
      lastNameControl: [this.registerForm.controls.lastNameControl.value, Validators.required],
      passFields: this.fb.group({
        passwordControl: [(this.registerForm.controls.passFields as FormGroup).controls.passwordControl.value, [Validators.minLength(6), Validators.required]],
        passwordValidControl: [(this.registerForm.controls.passFields as FormGroup).controls.passwordValidControl.value, [Validators.minLength(6), Validators.required]]
      }, { validators: samePasswordValidator })
    });

    if ( this.registerForm.invalid ) {
      return;
    }

    // Make an http post request with the register data
    this.registerService.register(this.registerInfo).subscribe(
      (result) => {
        if (!(result.SESSION_TOKEN === 'failed') && !(result.SESSION_TOKEN === 'used')) {
          this.cookieService.set('ST_TOKEN', result.SESSION_TOKEN, { path: '/' });
        }
        this.authToken = result;
        if(result.SESSION_TOKEN === 'failed' || result.SESSION_TOKEN === 'used') {
          return;
        }
        this.router.navigate(['/add-phone']);
      }
    );
  }

  passNotEmpty(): boolean {
    return ((this.registerForm.controls.passFields as FormGroup).controls.passwordControl.value === '') ? false : true;
  }

  passLength(): boolean {
    return (((this.registerForm.controls.passFields as FormGroup).controls.passwordControl.value as String).length < 6 );
  }

  emailUsed():boolean {
    return this.authToken.SESSION_TOKEN === 'used';
  }

  registerFailed():boolean {
    return this.authToken.SESSION_TOKEN === 'failed';
  }

}
