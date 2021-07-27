import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { RegisterService } from 'src/service/register-service/register.service';
import { RegisterInfo } from '../../interfaces/professional';


@Injectable({
  providedIn: 'root'
})
export class RegisterGuard implements CanActivate {
  subscription: Subscription;
  registerInfo: RegisterInfo;

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.registerInfo.email === '') {
      this.router.navigate(['/']);
      return false;
    }

    return true;
  }

  constructor(
    private router: Router,
    private cookieService: CookieService,
    private registerService: RegisterService
  ) {
    this.registerInfo = new RegisterInfo();
    this.subscription = this.registerService.oneFinished$.subscribe(
      regInf => {
        this.registerInfo = regInf;
      }
    )
  }

  ngOnDestroy() {
    // prevent memory leak when component destroyed
    this.subscription.unsubscribe();
  }



}
