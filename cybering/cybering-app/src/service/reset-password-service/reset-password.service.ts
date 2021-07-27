import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService extends BaseService {
  constructor(private http: HttpClient) {
    super();
  }

  resetPassword(email: SimpleString): Observable<SimpleString> {
    return this.simpleRequest(email, 'forgotpassword', this.http);
  }


}
