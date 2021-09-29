import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationInfo } from 'src/app/interfaces/notificationinfo';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NotificationService extends BaseService{

  constructor(private http : HttpClient) { 
    super();
  }

    getNotificationInfo(cookie: string): Observable<NotificationInfo[]> {
    return this.http.post<NotificationInfo[]>(
      this.extendurl('cybering/notifications'),
      null,
      {
        headers: {
          "Cookies": cookie,
          "action": "notifications-get"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }
}
