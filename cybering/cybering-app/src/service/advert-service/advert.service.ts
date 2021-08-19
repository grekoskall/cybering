import { Injectable, SimpleChange } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Advertisement, AdvertisementPost, AdvertisementApplication } from 'src/app/interfaces/advertisement';
import { retry, catchError } from 'rxjs/operators';
import { SimpleString } from 'src/app/interfaces/simplestring';

@Injectable({
  providedIn: 'root'
})
export class AdvertService extends BaseService {

  constructor(
    private http: HttpClient
  ) {
    super();
  }

  getApplications(cookie: string): Observable<AdvertisementApplication[]> {
    return this.http.post<AdvertisementApplication[]>(
      this.extendurl('cybering/advertisements/applications'),
      null,
      {
        headers: {
          "Cookies": cookie,
          "action": "get-apps"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  getAdvertisments(cookie: string): Observable<Advertisement[]> {
    return this.http.post<Advertisement[]>(
      this.extendurl('cybering/advertisements/home'),
      null,
      {
        headers: {
          "Cookies": cookie,
          "action": "get-ads"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  apply(cookie: string, adId: string): Observable<SimpleString> {
    let ad = new SimpleString(adId);
    return this.http.post<SimpleString>(
      this.extendurl('cybering/advertisements/home'),
      ad,
      {
        headers: {
          "Cookies": cookie,
          "action": "apply"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    ); 
  }

  post(cookie: string, title: string, desc: string, skills: string, date: string): Observable<SimpleString> {
    let ad = new AdvertisementPost();
    ad.title = title;
    ad.description = desc;
    ad.skills = skills;
    ad.endDate = date;
    return this.http.post<SimpleString>(
      this.extendurl('cybering/advertisements/post'),
      ad,
      {
        headers: {
          "Cookies": cookie,
          "action": "post"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    ); 
  }


}
