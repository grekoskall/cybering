import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { SignInValidatorService } from '../services/sign-in-validator/sign-in-validator.service';
import { sign } from 'crypto';

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {
  signInForm !: FormGroup;
  validatorService !: SignInValidatorService;


  constructor(validatorService: SignInValidatorService) {
    this.signInForm = validatorService.signInForm; 
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
    if( this.validatorService.isEmpty(this.signInForm) ) {
      this.signInForm = this.validatorService.initEmptyForm();
      return;
    } 

    // Form validation
    this.signInForm = this.validatorService.initValidationForm();


    // Make an http request
  }



}
