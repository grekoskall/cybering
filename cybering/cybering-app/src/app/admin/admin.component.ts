import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { AdminService } from 'src/service/admin-service/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  canObserve: boolean = false;
  constructor(
    private cookieService: CookieService,
    private adminService: AdminService,
    private router: Router
  ) {
    this.adminService.check(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        if (result.data === "ok") {
          this.canObserve = true;
        } else {
          this.cookieService.deleteAll();
          this.router.navigate(['/']);
        }
      }
    );
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.cookieService.deleteAll();
    this.router.navigate(['/']);
  }
}
