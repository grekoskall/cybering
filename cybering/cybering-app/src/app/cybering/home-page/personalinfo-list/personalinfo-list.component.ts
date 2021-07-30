import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ProfileInfoService } from 'src/service/profile-info-service/profile-info.service';
import { Professional } from 'src/app/interfaces/professional';

@Component({
  selector: 'app-personalinfo-list',
  templateUrl: './personalinfo-list.component.html',
  styleUrls: ['./personalinfo-list.component.css']
})
export class PersonalinfoListComponent implements OnInit {
  img_url = "assets/dpp.jpg";
  professional: Professional = new Professional;

  constructor(
    private cookieService: CookieService,
    private profileInfoService: ProfileInfoService
  ) {


  }

  ngOnInit(): void {



  }

}
