import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profid !: string | null;
  constructor(
    private cookieService: CookieService,
    private route: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.profid = this.route.snapshot.queryParamMap.get('id');
  }

}
