import { Component, OnInit } from '@angular/core';
import { FormBuilder, AbstractControl } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { SettingsService } from 'src/service/settings-service/settings.service';
import { Router } from '@angular/router';
import { PersonalInfoService } from 'src/service/personal-info-service/personal-info.service';
import { PersonalInfo, PrivacySettings, Privacy } from 'src/app/interfaces/personalinfo';
import { of } from 'rxjs';

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
  privacyWorkExperience: boolean = false;
  privacyEducation: boolean = false;
  privacySkills: boolean = false;
  _personalInfo !: PersonalInfo;
  index !: number;
  saveError: boolean = false;



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
        this.bioForm.controls.bio.setValue(result.bio);
        this.workPlaceForm.controls.workPlace.setValue(result.workPlace);
        this.workPositionForm.controls.workPosition.setValue(result.workPosition);

        this.index = 0;
        if (result.education != null) {
          for (var currentEducation of result.education) {
            this.index = this.index + 1;
            this.educationForm.addControl(this.index.toString(), this.fb.control(''));
            this.educationForm.get(this.index.toString())?.setValue(currentEducation);
          }
        }

        this.index = 0;
        if (result.workExperience != null) {
          for (var currentWorkExperience of result.workExperience) {
            this.index = this.index + 1;
            this.workExperienceForm.addControl(this.index.toString(), this.fb.control(''));
            this.workExperienceForm.get(this.index.toString())?.setValue(currentWorkExperience);
          }
        }

        this.index = 0;
        if (result.skills != null) {
          for (var currentSkills of result.skills) {
            this.index = this.index + 1;
            this.skillsForm.addControl(this.index.toString(), this.fb.control(''));
            this.skillsForm.get(this.index.toString())?.setValue(currentSkills);
          }
        }

        if (result.privacySettings.education.toString() == Privacy[Privacy.PRIVATE]) {
          let educationCheckbox = <HTMLInputElement>document.getElementById("educationCheckbox");
          this.privacyEducation = true;
        }

        if (result.privacySettings.workExperience.toString() == Privacy[Privacy.PRIVATE]) {
          let workExperienceCheckbox = <HTMLInputElement>document.getElementById("workExperienceCheckbox");
          this.privacyWorkExperience = true;
        }

        if (result.privacySettings.skills.toString() == Privacy[Privacy.PRIVATE]) {
          let skillsCheckbox = <HTMLInputElement>document.getElementById("skillsCheckbox");
          this.privacySkills = true;
        }

      }
    )
  }

  setPersonalInfo(): void {

    this.personalInfoService.setPersonalInfo(this.cookieService.get('ST_TOKEN'), this._personalInfo).subscribe(
      result => {
        if (result.data === "success") {
          window.location.reload();
        } else {
          this.saveError = true;
        }
      }
    )
  }

  onAddMoreEducation(): void {
    let _index = Object.keys(this.educationForm.controls).length + 1;

    this.educationForm.addControl(_index.toString(), this.fb.control(''));
  }

  onAddMoreWorkExperience(): void {
    let _index = Object.keys(this.workExperienceForm.controls).length + 1;

    this.workExperienceForm.addControl(_index.toString(), this.fb.control(''));
  }

  onAddMoreSkills(): void {
    let _index = Object.keys(this.skillsForm.controls).length + 1;

    this.skillsForm.addControl(_index.toString(), this.fb.control(''));
  }

  onSave(): void {
    this._personalInfo.workPosition = this.workPositionForm.controls.workPosition.value;
    this._personalInfo.workPlace = this.workPlaceForm.controls.workPlace.value;
    this._personalInfo.bio = this.bioForm.controls.bio.value;

    let educationCheckbox = <HTMLInputElement>document.getElementById("educationCheckbox");
    this.privacyEducation = educationCheckbox.checked;

    let workExperienceCheckbox = <HTMLInputElement>document.getElementById("workExperienceCheckbox");
    this.privacyWorkExperience = workExperienceCheckbox.checked;

    let skillsCheckbox = <HTMLInputElement>document.getElementById("skillsCheckbox");
    this.privacySkills = skillsCheckbox.checked;

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

      if (this.index > this._personalInfo.workExperience.length) {
        if (!(this.workExperienceForm.controls[key].value === "")) {
          this._personalInfo.workExperience.push(this.workExperienceForm.controls[key].value);
          this.index = this.index + 1;
        }
      } else {
        if (this.workExperienceForm.controls[key].value === "") {
          this._personalInfo.workExperience.splice(this.index, 1);
        } else {
          this._personalInfo.workExperience[this.index] = this.workExperienceForm.controls[key].value;
          this.index = this.index + 1;
        }
      }

    });

    this.index = 0;
    Object.keys(this.educationForm.controls).forEach(key => {
      if (this.index > this._personalInfo.education.length) {
        if (!(this.educationForm.controls[key].value === "")) {
          this._personalInfo.education.push(this.educationForm.controls[key].value);
          this.index = this.index + 1;
        }
      } else {
        if (this.educationForm.controls[key].value === "") {
          this._personalInfo.education.splice(this.index, 1);
        } else {
          this._personalInfo.education[this.index] = this.educationForm.controls[key].value;
          this.index = this.index + 1;
        }
      }

    });

    this.index = 0;
    Object.keys(this.skillsForm.controls).forEach(key => {
      if (this.index > this._personalInfo.skills.length) {
        if (!(this.skillsForm.controls[key].value === "")) {
          this._personalInfo.skills.push(this.skillsForm.controls[key].value);
          this.index = this.index + 1;
        }
      } else {
        if (this.skillsForm.controls[key].value === "") {
          this._personalInfo.skills.splice(this.index, 1);
        } else {
          this._personalInfo.skills[this.index] = this.skillsForm.controls[key].value;
          this.index = this.index + 1;
        }
      }

    });

    this.setPersonalInfo();
  }

  ngOnInit(): void {
  }

}
