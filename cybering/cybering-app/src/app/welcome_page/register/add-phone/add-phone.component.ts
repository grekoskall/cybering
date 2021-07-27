import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { RegisterService } from 'src/service/register-service/register.service';

@Component({
  selector: 'app-add-phone',
  templateUrl: './add-phone.component.html',
  styleUrls: ['./add-phone.component.css']
})
export class AddPhoneComponent implements OnInit {

  constructor(
    private cookieService: CookieService,
    private registerService: RegisterService) { }

  ngOnInit(): void {
    this.cookieService.deleteAll();
  }



}
