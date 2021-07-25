import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {
  signInForm = this.fb.group({
    emailControl: [''],
    passwordControl: ['']
  });

  constructor(private fb: FormBuilder) {
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


    // Make an http request
  }



}
