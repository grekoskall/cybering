import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private cookieService: CookieService) {

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.cookieService.check('ST_TOKEN') && !(this.cookieService.get('ST_TOKEN') === 'failed')
      && !(this.cookieService.get('ST_TOKEN') === 'used')
    ) {
      return true;
    }

    if (state.url.toString() === 'http://localhost:8080/') {
      this.router.navigate(['/']);
    } else if (state.url.toString() === 'http://localhost:8080/fullsignin') {
      this.router.navigate(['/fullsignin']);
    }

    this.router.navigate(['/']);
    return false;
  }

}
