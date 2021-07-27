import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  

  constructor(private http: HttpClient) { }

  authenticate(cookie: string, url: string ): boolean {
    if ( url === '' ) {
      //return this.http.post<string>(this.home_url, cookie);
    }
    //this.http.post<string>(url, cookie);
    return true;
  }
}
