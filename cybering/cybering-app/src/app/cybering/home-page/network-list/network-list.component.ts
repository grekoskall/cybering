import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { NetworkService } from 'src/service/network-list-service/network.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-network-list',
  templateUrl: './network-list.component.html',
  styleUrls: ['./network-list.component.css']
})
export class NetworkListComponent implements OnInit {
  connection_list !: String[][];


  constructor(
    cookieService: CookieService,
    networkService: NetworkService,
    router: Router) {
    networkService.getNetworkList(cookieService.get('ST_TOKEN')).subscribe(
      result => {
        this.connection_list = result;
      }
    );
  }

  ngOnInit(): void {
  }

}
