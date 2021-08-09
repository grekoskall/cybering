import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NetworkService extends BaseService{

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


}
