import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { SimpleString } from 'src/app/interfaces/simplestring';

@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService extends BaseService {
  constructor(private http: HttpClient) {
    super();
  }

  resetPassword(email: SimpleString): Observable<SimpleString> {
    let nnurl = this.extendurl('forgotpassword');
    return this.http.post<SimpleString>(nnurl, email).pipe(
      retry(2),
      catchError(this.handleError)
    )
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
}
