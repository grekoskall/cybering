import { Component, OnInit } from '@angular/core';
import { Network } from 'src/app/interfaces/network';
import { NetworkService } from 'src/service/network-list-service/network.service';
import { CookieService } from 'ngx-cookie-service';


@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {
  networkArray !: Network[];
  searchArray !: String[][];


  constructor(
    private networkService: NetworkService,
    private cookieService: CookieService
  ) {
    this.networkService.getNetwork(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        this.networkArray = result;
      }
    )
  }

  ngOnInit(): void {
  }


  search(searchName: string): void {

    this.networkService.search(this.cookieService.get('ST_TOKEN'), searchName).subscribe(
      result => {

        this.searchArray = result;
      }
    )

  }
}
