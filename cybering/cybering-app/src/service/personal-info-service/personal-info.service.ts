import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { PersonalInfoList } from 'src/app/interfaces/professional';
import { retry, catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { PersonalInfo } from 'src/app/interfaces/personalinfo';
import { SimpleString } from 'src/app/interfaces/simplestring';

@Injectable({
  providedIn: 'root'
})
export class PersonalInfoService extends BaseService {

  constructor(private http : HttpClient) { 
    super();
  }

    getPersonalInfo(cookie : string) : Observable<PersonalInfo> {
    return this.http.post<PersonalInfo>(
      this.extendurl('cybering/personalinfo'), 
      null,
      {headers: {
        "Cookies": cookie,
        "action": "personal-info-get"
      }}
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

    setPersonalInfo(cookie : string, personalInfo : PersonalInfo) : Observable<SimpleString> {

    return this.http.post<SimpleString>(
      this.extendurl('cybering/personalinfo'), 
      personalInfo,
      {headers: {
        "Cookies": cookie,
        "action": "personal-info-set"
      }}
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }
  
}
