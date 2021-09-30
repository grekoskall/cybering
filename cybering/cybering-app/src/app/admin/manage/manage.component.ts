import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/service/admin-service/admin.service';
import { CookieService } from 'ngx-cookie-service';
import { Professional } from 'src/app/interfaces/professional';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css']
})
export class ManageComponent implements OnInit {
  canObserve: boolean = false;
  professionalArray !: Professional[];

  constructor(
    private adminService: AdminService,
    private cookieService: CookieService
  ) {
    this.adminService.check(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        if (result.data === "ok") {
          this.canObserve = true;
        }
      }
    );

    this.adminService.professionalList(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        this.professionalArray = result;
      }
    );
  }

  ngOnInit(): void {
  }

}
