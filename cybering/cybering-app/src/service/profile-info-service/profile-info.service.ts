import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';
import { BaseService } from '../base.service';
import { catchError, retry } from 'rxjs/operators';
import { Professional, PersonalInfoList } from 'src/app/interfaces/professional';

@Injectable({
  providedIn: 'root'
})
export class ProfileInfoService extends BaseService {

  constructor(private http: HttpClient) {
    super();
  }

  getProfileInfo(cookie: string): Observable<PersonalInfoList> {
    return this.http.post<PersonalInfoList>(
      this.extendurl('cybering/home-page'),
      null,
      {
        headers: {
          'Cookies': cookie,
          'action': "profile-info-list"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }



}
