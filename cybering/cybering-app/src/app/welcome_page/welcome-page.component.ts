import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SignInInfo } from '../interfaces/signininfo';
import { SignInService } from 'src/service/sign-in-service/sign-in.service';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { SimpleString } from '../interfaces/simplestring';

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {

  signInInfo: SignInInfo;
  authToken: SimpleString;

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
    this.authToken = new SimpleString('');
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
     this.authToken = new SimpleString('');
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
        if (!(result.data === 'failed')) {
          this.cookieService.set('ST_TOKEN', result.data, { path: '/' });
        }
        this.authToken = result;
        this.router.navigate(['/cybering/home-page']);
      }
    );
  }

  signInFailed():boolean {
    return this.authToken.data === 'failed';
  }
}
