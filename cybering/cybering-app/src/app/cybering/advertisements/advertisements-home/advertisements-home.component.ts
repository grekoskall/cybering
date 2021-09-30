import { Component, OnInit } from '@angular/core';
import { Advertisement } from 'src/app/interfaces/advertisement';
import { CookieService } from 'ngx-cookie-service';
import { AdvertService } from 'src/service/advert-service/advert.service';

@Component({
  selector: 'app-advertisements-home',
  templateUrl: './advertisements-home.component.html',
  styleUrls: ['./advertisements-home.component.css']
})
export class AdvertisementsHomeComponent implements OnInit {
  advertisementArray !: Advertisement[];
  applicationSuccess: boolean | undefined = undefined;

  constructor(
    private cookieService: CookieService,
    private advertService: AdvertService
  ) {
    this.advertService.getAdvertisments(
      this.cookieService.get('ST_TOKEN')
    ).subscribe(
      result => {
        this.advertisementArray = result;
      }
    );
  }

  ngOnInit(): void {
  }

  apply(advId: String): void {
    this.advertService.apply(
      this.cookieService.get('ST_TOKEN'),
      advId
    ).subscribe(
      result => {
        if (result === null) {
          this.applicationSuccess = false;
        }
        if (result.data === 'success') {
          this.applicationSuccess = true;
        } else {
          this.applicationSuccess = false;
        }
      }
    );
  }


}

