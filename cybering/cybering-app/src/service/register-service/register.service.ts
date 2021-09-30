import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { RegisterInfo } from '../../app/interfaces/professional';
import { Observable, Subject } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { SimpleString } from 'src/app/interfaces/simplestring';

@Injectable({
  providedIn: 'root'
})
export class RegisterService extends BaseService {
  private oneFinishedSource = new Subject<RegisterInfo>();
  private twoFinishedSource = new Subject<File>();
  private thrFinishedSource = new Subject<string>();

  oneFinished$ = this.oneFinishedSource.asObservable();
  twoFinished$ = this.twoFinishedSource.asObservable();
  thrFinished$ = this.thrFinishedSource.asObservable();

  registerInfo: RegisterInfo = new RegisterInfo();

  constructor(private http: HttpClient) {
    super();
  }

  register(registerInfo: RegisterInfo): Observable<SimpleString> {
    return this.http.post<SimpleString>(this.extendurl('register'), registerInfo).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  registerStep1(registerInfo: RegisterInfo): Observable<SimpleString> {
    this.registerInfo = registerInfo;
    let ss: SimpleString = new SimpleString(registerInfo.email);
    return this.simpleRequest(ss, 'register', this.http);
  }

  updateStep1(registerInfo: RegisterInfo) {
    this.oneFinishedSource.next(registerInfo);
  }

  updateStep2(file: File) {
    this.twoFinishedSource.next(file);
  }

  updateStep3(phone: string) {
    this.thrFinishedSource.next(phone);
  }

  uploadProfilePhoto(photoToUpload: string | undefined,  cookie: string): Observable<SimpleString> {
    let photo = new SimpleString("");
    if (photoToUpload === undefined) {
      photo.data = "dpp.jpg";
    } else {
      photo.data = photoToUpload;
    }
  
    return this.http
      .post<SimpleString>
      (
        this.extendurl('register/add-phone'),
        photo,
        { 
          headers: {
            'Cookies': cookie,
            'action': "add-photo"
          }
        }
      ).pipe
      (
        retry(2),
        catchError(this.handleError)
      );
  }

  sendPhone(phone: string, cookie: string): Observable<SimpleString> {
    const phoneNumber: SimpleString = new SimpleString(phone);
    return this.http
      .post<SimpleString>
      (
        this.extendurl('register/add-phone'),
        phoneNumber,
        { 
          headers: {
            'Cookies': cookie,
            'action': "add-phone"
          }
        }
      ).pipe
      (
        retry(2),
        catchError(this.handleError)
      );
  }

  sendInfo(registerInfo: RegisterInfo, cookie: string): Observable<SimpleString> {
    return this.http
      .post<SimpleString>
      (
        this.extendurl('register/add-phone'),
        registerInfo,
        { 
          headers: {
            'Cookies': cookie,
            'action': "add-info"
          }
        }
      ).pipe
      (
        retry(2),
        catchError(this.handleError)
      );
  }

}
