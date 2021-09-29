import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProfessionalProfileInfo } from 'src/app/interfaces/professionalprofileinfo';
import { retry, catchError } from 'rxjs/operators';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { Network } from 'src/app/interfaces/network';

@Injectable({
  providedIn: 'root'
})
export class ProfileViewService extends BaseService {

  constructor(private http: HttpClient) {
    super();
  }

  getProfessionalProfileInfo(cookie: string, profid: string): Observable<ProfessionalProfileInfo> {
    let profidFromUrl = new SimpleString(profid);
    return this.http.post<ProfessionalProfileInfo>(
      this.extendurl('cybering/profile'),
      profidFromUrl,
      {
        headers: {
          "Cookies": cookie,
          "action": "professional-info-get"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  connectionRequest(cookie: string, profid: string): Observable<SimpleString> {

    let profidFromUrl = new SimpleString(profid);
    return this.http.post<SimpleString>(
      this.extendurl('cybering/profile'),
      profidFromUrl,
      {
        headers: {
          "Cookies": cookie,
          "action": "connect-request"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  articleList(cookie: string, profid: string): Observable<Object[]> {
    let profidFromUrl = new SimpleString(profid);
    return this.http.post<Object[]>(
      this.extendurl('cybering/profile'),
      profidFromUrl,
      {
        headers: {
          "Cookies": cookie,
          "action": "article-user-list"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  getNetwork(cookie: string, profid: string): Observable<Network[]> {
    let profidFromUrl = new SimpleString(profid);
    return this.http.post<Network[]>(
      this.extendurl('cybering/profile'),
      profidFromUrl,
      {
        headers: {
          "Cookies": cookie,
          "action": "network-user"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }


}
