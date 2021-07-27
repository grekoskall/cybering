import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { samePasswordValidator } from 'src/app/validators/same-password.directive';
import { Authorization } from 'src/app/interfaces/authorization';
import { CookieService } from 'ngx-cookie-service';
import { RegisterService } from 'src/service/register-service/register.service';
import { Router } from '@angular/router';
import { RegisterInfo } from 'src/app/interfaces/professional';


@Component({
  selector: 'app-basic-info',
  templateUrl: './basic-info.component.html',
  styleUrls: ['./basic-info.component.css']
})
export class BasicInfoComponent implements OnInit {
  @Output() registerEvent = new EventEmitter<RegisterInfo>();

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

    if (this.registerForm.invalid) {
      return;
    }

    this.registerService.registerStep1(this.registerInfo).subscribe(
      (result) => {
        if (!(result.data === 'failed') && !(result.data === 'used')) {
          this.cookieService.set('ST_TOKEN', result.data, { path: '/' });
        }
        this.authToken.SESSION_TOKEN = result.data;
        if (result.data === 'failed' || result.data === 'used') {
          return;
        }
        this.registerService.updateStep1(this.registerInfo);
        this.router.navigate(['register/add-phone']);
      }
    );
  }

  passNotEmpty(): boolean {
    if ((this.registerForm.controls.passFields as FormGroup).controls.passwordControl.value === undefined ) {
      return false;
    }
    return ((this.registerForm.controls.passFields as FormGroup).controls.passwordControl.value === '') ? false : true;
  }

  passLength(): boolean {
     if ((this.registerForm.controls.passFields as FormGroup).controls.passwordControl.value === undefined ) {
      return true;
    }
    return (((this.registerForm.controls.passFields as FormGroup).controls.passwordControl.value as String).length < 6);
  }

  emailUsed(): boolean {
    return this.authToken.SESSION_TOKEN === 'used';
  }

  registerFailed(): boolean {
    return this.authToken.SESSION_TOKEN === 'failed';
  }

}