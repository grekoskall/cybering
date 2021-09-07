import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SettingsService extends BaseService {

  constructor(private http : HttpClient) { 
    super();
  }

  getEmail(cookie : string) : Observable<SimpleString> {
    return this.http.post<SimpleString>(
      this.extendurl('cybering/settings'), 
      null,
      {headers: {
        "Cookies": cookie,
        "action": "settings-email-get"
      }}
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  setEmail(cookie : string, email : string) : Observable<SimpleString> {
    let ss = new SimpleString(email);
    return this.http.post<SimpleString>(
      this.extendurl('cybering/settings'), 
      ss,
      {headers: {
        "Cookies": cookie,
        "action": "settings-email-set"
      }}
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  setPassword(cookie : string, pwd : string) : Observable<SimpleString> {
    let ss = new SimpleString(pwd);
    return this.http.post<SimpleString>(
      this.extendurl('cybering/settings'), 
      ss,
      {headers: {
        "Cookies": cookie,
        "action": "settings-password-set"
      }}
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }
}
