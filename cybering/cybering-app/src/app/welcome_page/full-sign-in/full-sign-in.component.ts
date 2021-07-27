import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { SignInService } from 'src/service/sign-in-service/sign-in.service';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { SignInInfo } from 'src/app/interfaces/signininfo';
import { Authorization } from 'src/app/interfaces/authorization';

@Component({
  selector: 'app-full-sign-in',
  templateUrl: './full-sign-in.component.html',
  styleUrls: ['./full-sign-in.component.css']
})
export class FullSignInComponent implements OnInit {

  signInInfo: SignInInfo;
  authToken: Authorization;


  signinForm = this.fb.group({
    emailControl: [''],
    passwordControl: ['']
  });


  constructor(
    private fb: FormBuilder,
    private signInService: SignInService,
    private cookieService: CookieService,
    private router: Router
  ) {
    this.signInInfo = new SignInInfo();
    this.authToken = new Authorization();
  }

  ngOnInit(): void {
  }

  signinSubmit() {
    this.authToken = new Authorization();
    this.cookieService.deleteAll();
    if (this.signinForm.controls.emailControl.value === '' && this.signinForm.controls.passwordControl.value === '') {
      this.signinForm = this.fb.group({
        emailControl: [''],
        passwordControl: ['']
      });
      return;
    }

    this.signinForm = this.fb.group({
      emailControl: [this.signinForm.controls.emailControl.value, [Validators.required, Validators.email]],
      passwordControl: [this.signinForm.controls.passwordControl.value, [Validators.required]]
    });
    if (this.signinForm.invalid) {
      return;
    }

    this.signInService.signin(this.signInInfo).subscribe(
      (result) => {
        if (!(result.SESSION_TOKEN === 'failed')) {
          this.cookieService.set('ST_TOKEN', result.SESSION_TOKEN, { path: '/' });
        }
        this.authToken = result;
        this.router.navigate(['/home-page']);
        this.signinForm = this.fb.group({
          emailControl: [this.signinForm.controls.emailControl.value],
          passwordControl: [this.signinForm.controls.passwordControl.value]
        });
      }
    )
  }

  signInFailed(): boolean {
    if (this.authToken.SESSION_TOKEN === 'failed') {
      return true;
    } else {
      return false;
    }
  }

}
