import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { AdvertService } from 'src/service/advert-service/advert.service';

@Component({
  selector: 'app-advertisement-post',
  templateUrl: './advertisement-post.component.html',
  styleUrls: ['./advertisement-post.component.css']
})
export class AdvertisementPostComponent implements OnInit {
  adForm = this.fb.group({
    adTitle: [''],
    adDesc: [''],
    adSkills: [''],
    adEnd: [''],
  });
  postFailed: boolean | undefined = undefined;

  constructor(
    private fb: FormBuilder,
    private cookieService: CookieService,
    private advertService: AdvertService
  ) { }

  ngOnInit(): void {
  }

  post() {
    this.adForm = this.fb.group({
      adTitle: [this.adForm.controls.adTitle.value, Validators.required],
      adDesc: [this.adForm.controls.adDesc.value, Validators.required],
      adSkills: [this.adForm.controls.adSkills.value],
      adEnd: [this.adForm.controls.adEnd.value],
    });

    if (this.adForm.invalid) {
      return;
    }

    this.advertService.post(
      this.cookieService.get('ST_TOKEN'),
      this.adForm.controls.adTitle.value,
      this.adForm.controls.adDesc.value,
      this.adForm.controls.adSkills.value,
      this.adForm.controls.adEnd.value
    ).subscribe(
      result => {
        if ( result.data === 'success') {
          this.postFailed = false;
        } else {
          this.postFailed = true;
        }
      }
    )
  }



}
