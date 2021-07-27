import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { RegisterInfo } from '../../app/interfaces/professional';
import { Authorization } from 'src/app/interfaces/authorization';
import { Observable, Subject } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { SimpleString } from 'src/app/interfaces/simplestring';

@Injectable({
  providedIn: 'root'
})
export class RegisterService extends BaseService{
  private oneFinishedSource = new Subject<RegisterInfo>();
  private twoFinishedSource = new Subject<SimpleString>();
  private thrFinishedSource = new Subject<SimpleString>();

  oneFinished$ = this.oneFinishedSource.asObservable();

  constructor(private http: HttpClient) {
    super();
  }

  register(registerInfo: RegisterInfo): Observable<Authorization> {
    return this.http.post<Authorization>(this.extendurl('register'), registerInfo).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  registerStep1(registerInfo: RegisterInfo): Observable<SimpleString> {
    let ss: SimpleString = new SimpleString(registerInfo.email);
    return this.simpleRequest(ss, 'register', this.http);
  }

  updateStep1(registerInfo: RegisterInfo) {
    this.oneFinishedSource.next(registerInfo);
  }

}
