import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-full-sign-in',
  templateUrl: './full-sign-in.component.html',
  styleUrls: ['./full-sign-in.component.css']
})
export class FullSignInComponent implements OnInit {

  signinForm = new FormGroup({
    emailControl: new FormControl(''),
    passwordControl: new FormControl('')
  });


  constructor() { }

  ngOnInit(): void {
  }

  signinSubmit() {

    if (this.signinForm.controls.emailControl.value === '') {
      this.signinForm = new FormGroup({
        emailControl: new FormControl(''),
        passwordControl: new FormControl('')
      });
      return;
    }
    this.signinForm = new FormGroup({
      emailControl: new FormControl(this.signinForm.controls.emailControl.value, [
        Validators.required, Validators.email
      ]),
      passwordControl: new FormControl(this.signinForm.controls.passwordControl.value, Validators.required)
    });

  }

}
