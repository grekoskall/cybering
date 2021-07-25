import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SignInInfo } from '../interfaces/signininfo';
import { SignInService } from 'src/service/sign-in-service/sign-in.service';
import { Authorization } from '../interfaces/authorization';
import { SafeSubscriber } from 'rxjs/internal/Subscriber';

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

  constructor(private fb: FormBuilder, private signInService: SignInService) {
    this.signInInfo = new SignInInfo();
    this.authToken = new Authorization();
  }

  ngOnInit(): void {
  }

  onSigninSubmit() {
    // Make a post request to the server and
    // get a response.
    // If the response is positive (OK + sessionToken)
    // Save the above to sessionStorage and redirect to home page
    // If the response is negative (NO )
    // Show error message above submit button
    this.authToken.SESSION_TOKEN = "hello";
    // Reset form validator group at empty values
    if (this.signInForm.controls.emailControl.value === "" && this.signInForm.controls.passwordControl.value === "") {
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
    if ( this.signInForm.invalid ) {
      return;
    }
    

    // Make an http request and get Session token
    this.signInService.signin(this.signInInfo).subscribe(result => this.authToken.SESSION_TOKEN = result.SESSION_TOKEN);
    
    // Update headers to always append the token at the head
    this.signInService.updateToken(this.authToken);

    // Send full log in request

    // Get redirected to new page.

  }

}
