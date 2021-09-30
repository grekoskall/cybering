import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { RegisterService } from 'src/service/register-service/register.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription, throwError } from 'rxjs';
import { RegisterInfo } from 'src/app/interfaces/professional';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-phone',
  templateUrl: './add-phone.component.html',
  styleUrls: ['./add-phone.component.css']
})
export class AddPhoneComponent implements OnInit {

  registerInfo!: RegisterInfo;
  default_url: string = "assets/dpp.jpg"
  image_url: string | undefined = "dpp.jpg";
  phone: string = '';
  photoToUpload: File | null = null;
  response: string = 'unsent';
  photo: File | null = null;

  onetoForm = this.fb.group({
    phoneControl: [''],
    photoControl: ['']
  });

  constructor(
    private cookieService: CookieService,
    private registerService: RegisterService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.registerInfo = this.registerService.registerInfo;
  }

  ngOnInit(): void {

  }

  reloadImg(event: any) {
    var files = event.target.files;
    if (files.length === 0) {
      return;
    }
    var file = files[0];
    this.default_url = "assets/" + file.name;
  }

  pathStrip(image_url: string | undefined): string | undefined {
    if (image_url != undefined) {
      image_url = image_url.split("/").pop();
      if (image_url != undefined)
        image_url = image_url.split("\\").pop();
    }

    return image_url;
  }

  submitOnetoForm() {
    this.onetoForm = this.fb.group({
      photoControl: [this.onetoForm.controls.photoControl.value],
      phoneControl: [this.onetoForm.controls.phoneControl.value, [Validators.required, Validators.pattern('69([0-9])*')]]
    });

    if (this.onetoForm.invalid) {
      return;
    }

    this.image_url = this.onetoForm.controls.photoControl.value;
    this.image_url = this.pathStrip(this.image_url);

    this.registerService.uploadProfilePhoto
      (
        this.image_url,
        this.cookieService.get('ST_TOKEN')
      ).subscribe
      (
        result => {
          if (result.data === 'ok') {
            this.response = 'image sent ok';
          } else {
            this.response = 'image sent no';
            return;
          }

          this.registerService.sendInfo
            (
              this.registerInfo,
              this.cookieService.get('ST_TOKEN')
            ).subscribe
            (
              result => {
                if (result.data === 'ok') {
                  this.response = 'info sent ok';
                } else {
                  this.response = 'info sent no';
                  return;
                }

                this.registerService.sendPhone
                  (
                    this.onetoForm.controls.phoneControl.value,
                    this.cookieService.get('ST_TOKEN')
                  ).subscribe
                  (
                    result => {
                      if (result.data != 'failed') {
                        this.response = 'phone sent ok';
                        this.cookieService.set('ST_TOKEN', result.data);
                        this.router.navigate(['/cybering/home-page']);
                      } else {
                        this.response = 'phone sent no';
                        return;
                      }
                    }
                  )
              }
            )
        }
      );

  }

  updateInfo(registerInfo: RegisterInfo) {
    this.registerInfo = registerInfo;
  }

}
