import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { SettingsService } from 'src/service/settings-service/settings.service';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { samePasswordValidator } from 'src/app/validators/same-password.directive';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  email !: string;
  emailForm = this.fb.group({
    newEmail: ['']
  });
  passwordForm = this.fb.group({
    oldPassword: [''],
    newPasswordFields: this.fb.group({
      passwordControl: [''],
      passwordValidControl: ['']
    }, { validators: samePasswordValidator })
  });
  emailCheck: number = 0;
  passwordCheck: string = '';

  constructor(
    private cookieService: CookieService,
    private settingsService: SettingsService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.settingsService.getEmail(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        if (result.data === 'failed') {
          this.cookieService.deleteAll();
          this.router.navigate(['/']);
        }
        this.email = result.data;
        this.emailForm.controls.newEmail.setValue(this.email);
      }
    )
  }

  setEmail(): void {
    this.emailForm = this.fb.group({
      newEmail: [this.emailForm.controls.newEmail.value, [Validators.required, Validators.email]]
    });
    if (this.emailForm.invalid)
      return;
    this.settingsService.setEmail(this.cookieService.get('ST_TOKEN'), this.emailForm.controls.newEmail.value).subscribe(
      result => {
        if (result.data === 'invalid') {
          this.emailCheck = -1;
        } else if (result.data === 'exists') {
          this.emailCheck = -2;
        } else if (result.data === 'success') {
          this.emailCheck = 1;
        }
      }
    )
  }

  onEmailType(): void {
    this.emailCheck = 0;
  }

  setPassword(): void {
    this.passwordForm = this.fb.group({
      oldPassword: [this.passwordForm.controls.oldPassword.value, Validators.required],
      newPasswordFields: this.fb.group({
        passwordControl: [(this.passwordForm.controls.newPasswordFields as FormGroup).controls.passwordControl.value, [Validators.minLength(6), Validators.required]],
        passwordValidControl: [(this.passwordForm.controls.newPasswordFields as FormGroup).controls.passwordValidControl.value, [Validators.minLength(6), Validators.required]]
      }, { validators: samePasswordValidator })
    });
    if (this.passwordForm.invalid) {
      if (this.passwordForm.controls.oldPassword.invalid)
        this.passwordCheck = 'oldPassword-invalid';
      else if ((this.passwordForm.controls.newPasswordFields as FormGroup).controls.passwordControl.invalid)
        this.passwordCheck = 'newPassword-small';
      else if (this.passwordForm.controls.newPasswordFields.invalid)
        this.passwordCheck = 'password-notSame';

      return;
    }
    this.settingsService.setPassword(
      this.cookieService.get('ST_TOKEN'),
      this.passwordForm.controls.oldPassword.value,
      (this.passwordForm.controls.newPasswordFields as FormGroup).controls.passwordControl.value).subscribe(
        result => {
          if (result.data === 'failed') {
            this.passwordCheck = 'failed';
          } else if (result.data === 'success') {
            this.passwordCheck = 'success';
            this.passwordForm = this.fb.group({
              oldPassword: [''],
              newPasswordFields: this.fb.group({
                passwordControl: [''],
                passwordValidControl: ['']
              }, { validators: samePasswordValidator })
            });
          }
        }
      )
  }

  ngOnInit(): void {
  }

}
