import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SignInInfo } from '../interfaces/signininfo';
import { SignInService } from 'src/service/sign-in-service/sign-in.service';
import { Authorization } from '../interfaces/authorization';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {

  signInInfo: SignInInfo;
  authToken: Authorization;

  signInForm = this.fb.group({
    emailControl: [''],
    passwordControl: ['']
  });

  constructor(
    private fb: FormBuilder,
    private signInService: SignInService,
    private cookieService: CookieService,
    private router: Router) {
    this.signInInfo = new SignInInfo();
    this.authToken = new Authorization();
  }

  ngOnInit(): void {
    this.cookieService.deleteAll();
  }

  onSigninSubmit() {
    // Make a post request to the server and
    // get a response.
    // If the response is positive (OK + sessionToken)
    // Save the above to sessionStorage and redirect to home page
    // If the response is negative (NO )
    // Show error message above submit button
    // Reset form validator group at empty values
     this.authToken = new Authorization();
     this.cookieService.deleteAll();
    if (this.signInForm.controls.emailControl.value === '' && this.signInForm.controls.passwordControl.value === '') {
      this.signInForm = this.fb.group({
        emailControl: [''],
        passwordControl: ['']
      });
      return;
    }

    // Form validation
    this.signInForm = this.fb.group({
      emailControl: [this.signInForm.controls.emailControl.value, [Validators.required, Validators.email]],
      passwordControl: [this.signInForm.controls.passwordControl.value, Validators.required]
    });
    if (this.signInForm.invalid) {
      return;
    }

    // Make an http request and get Session token and redirect
    this.signInService.signin(this.signInInfo).subscribe(
      (result) => {
        if (!(result.SESSION_TOKEN === 'failed')) {
          this.cookieService.set('ST_TOKEN', result.SESSION_TOKEN, { path: '/' });
        }
        this.authToken = result;
        this.router.navigate(['/home-page']);
      }
    );
  }

  signInFailed():boolean {
    return this.authToken.SESSION_TOKEN === 'failed';
  }
}
