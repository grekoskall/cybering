import { Injectable } from '@angular/core';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, retry } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BaseService {
  url = 'https://localhost:8080/';
  
  constructor() { }

  extendurl(suff: string): string {
    return this.url+suff;
  }

  simpleRequest(data: SimpleString, path: string, http: HttpClient): Observable<SimpleString> {
    let new_url = this.extendurl(path);
    return http.post<SimpleString>(new_url, data).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

    public handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }
}
