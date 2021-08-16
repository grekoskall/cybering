import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { retry, catchError } from 'rxjs/operators';
import { Professional } from 'src/app/interfaces/professional';

@Injectable({
  providedIn: 'root'
})
export class AdminService extends BaseService {

  constructor(private http: HttpClient) {
    super();
  }

  check(cookie: string): Observable<SimpleString> {
    return this.http.post<SimpleString>(
      this.extendurl('admin/cybering'),
      null,
      {
        headers: {
          'Cookies': cookie,
          'action': "admin-check"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  professionalList(cookie: string): Observable<Professional[]> {
    return this.http.post<Professional[]>(
      this.extendurl('admin/cybering'),
      null,
      {
        headers: {
          'Cookies': cookie,
          'action': "admin-list"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  
}