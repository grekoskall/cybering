import { Component, OnInit } from '@angular/core';
import { AdvertisementApplication } from 'src/app/interfaces/advertisement';
import { CookieService } from 'ngx-cookie-service';
import { AdvertService } from 'src/service/advert-service/advert.service';

@Component({
  selector: 'app-advertisements-applications',
  templateUrl: './advertisements-applications.component.html',
  styleUrls: ['./advertisements-applications.component.css']
})
export class AdvertisementsApplicationsComponent implements OnInit {
  applicationsArray !: AdvertisementApplication[];

  constructor(
    private cookieService: CookieService,
    private advertService: AdvertService
  ) { 
    this.advertService.getApplications(
      this.cookieService.get('ST_TOKEN')
    ).subscribe(
      result => {
        this.applicationsArray = result;
      }
    );
  }

  ngOnInit(): void {
  }

}
