import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { SettingsService } from 'src/service/settings-service/settings.service';
import { Router } from '@angular/router';
import { PersonalInfoService } from 'src/service/personal-info-service/personal-info.service';
import { PersonalInfo, PrivacySettings, Privacy } from 'src/app/interfaces/personalinfo';

@Component({
  selector: 'app-personalinfo',
  templateUrl: './personalinfo.component.html',
  styleUrls: ['./personalinfo.component.css']
})
export class PersonalinfoComponent implements OnInit {

  workPositionForm = this.fb.group({
    workPosition: ['']
  });
  workPlaceForm = this.fb.group({
    workPlace: ['']
  });
  bioForm = this.fb.group({
    bio: ['']
  });
  workExperienceForm = this.fb.group({

  });
  educationForm = this.fb.group({

  });
  skillsForm = this.fb.group({

  });
  privacyWorkExperience !: boolean;
  privacyEducation !: boolean;
  privacySkills !: boolean;
  _personalInfo !: PersonalInfo;
  index !: number;

  constructor(
    private cookieService: CookieService,
    private personalInfoService: PersonalInfoService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.personalInfoService.getPersonalInfo(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        if (result === null) {
          this.cookieService.deleteAll();
          this.router.navigate(['/']);
        }
        this._personalInfo = result;
      }
    )
  }

  setPersonalInfo(): void {

    this.personalInfoService.setPersonalInfo(this.cookieService.get('ST_TOKEN'), this._personalInfo).subscribe(
      result => {

      }
    )
  }

  onSave(): void {
    this._personalInfo.workPosition = this.workPositionForm.controls.workPosition.value;
    this._personalInfo.workPlace = this.workPlaceForm.controls.workPlace.value;
    this._personalInfo.bio = this.bioForm.controls.bio.value;

    if (this.privacyWorkExperience === true)
      this._personalInfo.privacySettings.workExperience = Privacy.PRIVATE;
    else
      this._personalInfo.privacySettings.workExperience = Privacy.PUBLIC;
    if (this.privacyEducation === true)
      this._personalInfo.privacySettings.education = Privacy.PRIVATE;
    else
      this._personalInfo.privacySettings.education = Privacy.PUBLIC;
    if (this.privacySkills === true)
      this._personalInfo.privacySettings.skills = Privacy.PRIVATE;
    else
      this._personalInfo.privacySettings.skills = Privacy.PUBLIC;

    //TODO: append new item to the String arrays if a new one was added to the forms
    this.index = 0;
    Object.keys(this.workExperienceForm.controls).forEach(key => {
      if (this.workExperienceForm.controls[key].value === null) {
        this._personalInfo.workExperience.splice(this.index, 1);
      } else {
        this._personalInfo.workExperience[this.index] = this.workExperienceForm.controls[key].value;
      }

      this.index = this.index + 1;
    });

    this.index = 0;
    Object.keys(this.educationForm.controls).forEach(key => {
      if (this.educationForm.controls[key].value === null) {
        this._personalInfo.education.splice(this.index, 1);
      } else {
        this._personalInfo.education[this.index] = this.educationForm.controls[key].value;
      }

      this.index = this.index + 1;
    });

    this.index = 0;
    Object.keys(this.skillsForm.controls).forEach(key => {
      if (this.skillsForm.controls[key].value === null) {
        this._personalInfo.skills.splice(this.index, 1);
      } else {
        this._personalInfo.skills[this.index] = this.skillsForm.controls[key].value;
      }

      this.index = this.index + 1;
    });

    this.setPersonalInfo();
  }

  ngOnInit(): void {
  }

}
