import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaderResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Authorization } from 'src/app/interfaces/authorization';
import { SignInInfo } from 'src/app/interfaces/signininfo';

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

  signin(info: SignInInfo): Observable<Authorization> {
    return this.http.post<Authorization>(this.url, info).pipe(
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


  updateToken(authToken: Authorization) {
    httpOptions.headers = httpOptions.headers.set('SessionToken', authToken.SESSION_TOKEN);
  }

  
}
