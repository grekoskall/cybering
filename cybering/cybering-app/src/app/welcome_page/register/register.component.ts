import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { RegisterService } from 'src/service/register-service/register.service';
import { BasicInfoComponent } from './basic-info/basic-info.component';
import { RegisterInfo } from 'src/app/interfaces/professional';
import { CookieService } from 'ngx-cookie-service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-basic-info',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [RegisterService]
})
export class RegisterComponent implements OnInit {
  registerInfo: RegisterInfo;
  photoToUpload: File | null = null;
  phone: string =  '';
  subscription1: Subscription;

  constructor(
    private cookieService: CookieService,
    private registerService: RegisterService
  ) {
    this.registerInfo = new RegisterInfo();
    this.subscription1 = this.registerService.oneFinished$.subscribe(
      regInf => {
        this.registerInfo = regInf;
      }
    );
  }

  ngOnInit(): void {
    this.cookieService.deleteAll();
  }

  updateInfo(registerInfo: RegisterInfo) {
    this.registerInfo = registerInfo;
  }

  ngOnDestroy() {
    // prevent memory leak when component destroyed
    this.subscription1.unsubscribe();

  }

}