import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';
import { BaseService } from '../base.service';
import { catchError, retry } from 'rxjs/operators';
import { Professional } from 'src/app/interfaces/professional';

@Injectable({
  providedIn: 'root'
})
export class ProfileInfoService extends BaseService {

  constructor(private http: HttpClient) {
    super();
  }

  getProfileInfo(cookie: string): Observable<Professional> {
    return this.http.post<Professional>(
      this.extendurl('cybering/home-page'),
      null,
      {
        headers: {
          'Cookies': cookie,
          'action': "profile-info"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }


}
