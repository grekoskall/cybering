import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  emailGroup = new FormGroup({
    emailControl: new FormControl('')
  });

  constructor() { }

  ngOnInit(): void {
  }

  emailSubmit() {

    this.emailGroup = new FormGroup({
      emailControl: new FormControl(this.emailGroup.controls.emailControl.value, [
        Validators.required, Validators.email
      ])
    });


  }

}
