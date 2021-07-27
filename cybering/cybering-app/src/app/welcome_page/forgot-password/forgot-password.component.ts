import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ResetPasswordService } from 'src/service/reset-password-service/reset-password.service';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  emailSent: boolean | undefined;

  data!: SimpleString;

  emailGroup = new FormGroup({
    emailControl: new FormControl('')
  });

  constructor(private resetService: ResetPasswordService,
    private cookieService: CookieService) {
    this.data = new SimpleString('');
   }

  ngOnInit(): void {
    this.cookieService.deleteAll();
  }

  emailSubmit() {
    this.emailGroup = new FormGroup({
      emailControl: new FormControl(this.emailGroup.controls.emailControl.value, [
        Validators.required, Validators.email
      ])
    });

    if (this.emailGroup.invalid) {
      return;
    }

    this.data.data = this.emailGroup.controls.emailControl.value;
    this.resetService.resetPassword(this.data).subscribe(
      (result) => {
        this.data = result;
        if (this.data.data === 'failed') {
          this.emailSent = false;
        } else if (this.data.data === 'success') {
          this.emailSent = true;
        }
      }
    );


  }
}
