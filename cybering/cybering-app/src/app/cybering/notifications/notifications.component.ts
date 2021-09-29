import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/service/notification-service/notification.service';
import { NotificationInfo, NotificationType } from 'src/app/interfaces/notificationinfo';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  notificationInfoArray !: NotificationInfo[];

  constructor(
    private cookieService: CookieService,
    private notificationService: NotificationService,
    private router: Router
  ) {
    this.notificationService.getNotificationInfo(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        if (result === null) {
          this.cookieService.deleteAll();
          this.router.navigate(['/']);
        }
        this.notificationInfoArray = result;

      }
    )
  }


  public get notificationType(): typeof NotificationType {
    return NotificationType; 
  }

  ngOnInit(): void {
  }

}
