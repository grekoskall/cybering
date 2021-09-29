import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ProfileViewService } from 'src/service/profile-view-service/profile-view.service';
import { ProfessionalProfileInfo } from 'src/app/interfaces/professionalprofileinfo';
import { Article } from 'src/app/interfaces/article';
import { Network } from 'src/app/interfaces/network';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profidParam !: string | null;
  professionalProfileInfo !: ProfessionalProfileInfo;
  articles_list !: Article[];
  comment_list: boolean[] = [];
  network_list !: Network[];
  img_url: String = '';

  constructor(
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private router: Router,
    public sanitizer: DomSanitizer,
    private profileViewService: ProfileViewService
  ) {
    this.profidParam = this.route.snapshot.queryParamMap.get('id');
    if (this.profidParam === null || this.profidParam.length <= 0 || this.profidParam === undefined) {
      this.router.navigate(['/userNotFound']);
    } else {
      this.profileViewService.getProfessionalProfileInfo(this.cookieService.get('ST_TOKEN'), this.profidParam.toString()).subscribe(
        result => {
          if (result === null) {
            this.router.navigate(['/userNotFound']);
          }
          this.professionalProfileInfo = result;
          this.initUserImg();
        }
      );
      this.profileViewService.articleList(this.cookieService.get('ST_TOKEN'), this.profidParam.toString()).subscribe(
        result => {
          this.articles_list = result as Article[];
          for (let article of this.articles_list) {
            this.comment_list.push(false);
          }
        }
      );
      this.profileViewService.getNetwork(this.cookieService.get('ST_TOKEN'), this.profidParam.toString()).subscribe(
        result => {
          this.network_list = result;
        }
      );
    }
  }

  onClickRequest() {
    if (this.profidParam != null) {
      this.profileViewService.connectionRequest(this.cookieService.get('ST_TOKEN'), this.profidParam.toString()).subscribe(
        result => {
          if (result.data != "success") {
            this.cookieService.deleteAll();
            this.router.navigate(['/']);
          } else {
            window.location.reload();
          }
        }
      );
    }
  }

  onClickMessage() {
    if (this.profidParam != null) {
      let navUrl = "/cybering/discussions";
      this.router.navigate([navUrl], { queryParams: {id: this.profidParam}});
    }
  }

  initUserImg() {
    if (this.professionalProfileInfo.photo != 'default' && this.professionalProfileInfo.photo != '') {
      this.img_url = 'assets/' + this.professionalProfileInfo.photo;
    } else {
      this.img_url = 'assets/dpp.jpg';
    }
  }

  ngOnInit(): void {


  }
}
