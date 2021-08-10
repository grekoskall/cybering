import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ProfileInfoService } from 'src/service/profile-info-service/profile-info.service';
import { Professional, PersonalInfoList } from 'src/app/interfaces/professional';
import { Router } from '@angular/router';

@Component({
  selector: 'app-personalinfo-list',
  templateUrl: './personalinfo-list.component.html',
  styleUrls: ['./personalinfo-list.component.css']
})
export class PersonalinfoListComponent implements OnInit {
  img_url: String = '';
  info: PersonalInfoList = new PersonalInfoList;

  constructor(
    private cookieService: CookieService,
    private profileInfoService: ProfileInfoService,
    private router: Router
  ) {

    this.profileInfoService.getProfileInfo(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        this.info = result;
        if (this.info.photo_url === '' && this.info.firstName === '') {
          this.router.navigate(['/']);
        }
        if (this.info.photo_url != 'default' && this.info.photo_url != '') {
          this.img_url = 'assets/' + this.info.photo_url;
        } else {
          this.img_url = 'assets/dpp.jpg';
        }
        if (this.img_url === '') {
          this.router.navigate(['/']);
        }
      }
    );
  }

  ngOnInit(): void {

  }

}
