import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from 'src/service/articles-service/article.service';
import { Article } from 'src/app/interfaces/article';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {
  articles_list !: Article[];
  firstName !: String;
  lastName !: String;
  comment_list: boolean[] = [];


  constructor(
    private cookieService: CookieService,
    private articleService: ArticleService,
    public sanitizer: DomSanitizer
  ) {

    this.articleService.getArticlesList(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        this.articles_list = result as Article[];
        for(let article of this.articles_list) {
          this.comment_list.push(false);
        }
      }
    );
   }

   

  ngOnInit(): void {
  }

  changeReply(index: number) {
    this.comment_list[index] = !this.comment_list[index];
  }

}
