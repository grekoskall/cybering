import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';


@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {

  signinForm = new FormGroup({
    emailControl: new FormControl(''),
    passControl: new FormControl(''),
  });

  constructor() { }

  ngOnInit(): void {

  }

  onSigninSubmit() {
    // Make a post request to the server and
    // get a response.
    // If the response is positive (OK + sessionToken)
    // Save the above to sessionStorage and redirect to home page
    // If the response is negative (NO )
    // Show error message above submit button

    // First validate
    if (this.signinForm.controls.emailControl.value === '' && this.signinForm.controls.passControl.value === '') {
      this.signinForm = new FormGroup({
        emailControl: new FormControl(''),
        passControl: new FormControl(''),
      });
      return;
    }

    this.signinForm = new FormGroup({
      emailControl: new FormControl(this.signinForm.controls.emailControl.value, [
        Validators.email,
        Validators.required
      ]),
      passControl: new FormControl(this.signinForm.controls.passControl.value, Validators.required),
    });
  }

}
