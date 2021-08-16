import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { Network } from 'src/app/interfaces/network';
import { SimpleString } from 'src/app/interfaces/simplestring';

@Injectable({
  providedIn: 'root'
})
export class NetworkService extends BaseService {

  constructor(private http: HttpClient) {
    super();
  }

  getNetworkList(cookie: string): Observable<String[][]> {
    return this.http.post<String[][]>(
      this.extendurl('cybering/home-page'),
      null,
      {
        headers: {
          'Cookies': cookie,
          'action': "network-list"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  getNetwork(cookie: string): Observable<Network[]> {
    return this.http.post<Network[]>(
      this.extendurl("cybering/network"),
      null,
      {
        headers: {
          'Cookies': cookie,
          'action': "network"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  search(cookie: string, searchName: string): Observable<String[][]> {
    let ss = new SimpleString(searchName);
    return this.http.post<String[][]>(
      this.extendurl("cybering/network"),
      ss,
      {
        headers: {
          'Cookies': cookie,
          'action': "search"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }


}
