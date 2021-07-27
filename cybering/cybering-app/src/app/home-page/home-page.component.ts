import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from '../../service/auth-service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  home_url = 'http://localhost:8080/home-page';

  constructor(
    private cookieService: CookieService,
    private authService: AuthService,
    private router: Router) {

  }

  ngOnInit(): void {


  }



}
