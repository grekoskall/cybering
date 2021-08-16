import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaderResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

import { SignInInfo } from 'src/app/interfaces/signininfo';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { SignInReply } from 'src/app/interfaces/signinreply';

const httpOptions = {
  headers: new HttpHeaders({
    Authorize: '',
    SessionToken: ''
  })
};

@Injectable()
export class SignInService {
  url = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  signin(info: SignInInfo): Observable<SignInReply> {
    return this.http.post<SignInReply>(this.url, info).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
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


  updateToken(authToken: SimpleString) {
    httpOptions.headers = httpOptions.headers.set('SessionToken', authToken.data);
  }

  
}
