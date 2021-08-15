import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from 'src/service/articles-service/article.service';
import { Article } from 'src/app/interfaces/article';
import { DomSanitizer } from '@angular/platform-browser';
import { FormBuilder, Validators } from '@angular/forms';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { Router } from '@angular/router';
import { ArticleReply } from 'src/app/interfaces/articlereply';

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
  server_reply !: SimpleString;
  comment_success: number = 0;
  interest_success: number = 0;
  article_reply: ArticleReply = new ArticleReply();

  replyForm = this.fb.group({
    replyText: ['']
  });

  constructor(
    private cookieService: CookieService,
    private articleService: ArticleService,
    public sanitizer: DomSanitizer,
    private fb: FormBuilder,
    private router: Router
  ) {

    this.articleService.getArticlesList(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        this.articles_list = result as Article[];
        for (let article of this.articles_list) {
          this.comment_list.push(false);
        }
      }
    );
  }



  ngOnInit(): void {
  }

  changeReply(index: number): void {
    this.comment_list[index] = !this.comment_list[index];
  }

  reply(id: String, index: number): void {
    //Post request to server
    this.article_reply.aid = id.toString();
    this.article_reply.reply = this.replyForm.controls.replyText.value;
    this.articleService.replyToArticle(
      this.cookieService.get('ST_TOKEN'),
      this.article_reply
    ).subscribe(
      result => {
        this.server_reply = result;
        if (this.server_reply.data != 'failed') {
          this.comment_success = 1;
          this.router.onSameUrlNavigation = "reload";
          this.router.navigate([this.router.url]);
        } else {
          this.comment_success = -1;
          this.replyForm = this.fb.group({
            replyText: ['']
          }
          );
          this.changeReply(index);
        }
      }
    )
  }

  resetCommentMessage() {
    this.comment_success = 0;
  }

  changeInterest(id: String, index: number): void {

    this.articleService.changeInterestToArticle(
      this.cookieService.get('ST_TOKEN'),
      new SimpleString(id.toString())
    ).subscribe(
      result => {
        if (result.data != "failed") {
          this.interest_success = 1;
          if(this.articles_list[index].interested) {
            this.articles_list[index].likes.length = this.articles_list[index].likes.length - 1;
          } else {
            this.articles_list[index].likes.length = this.articles_list[index].likes.length + 1;
          }
          this.articles_list[index].interested = !this.articles_list[index].interested;
        } else {
          this.interest_success = -1;
        }
      }
    );
  }

  resetInterestSuccess() {
    this.interest_success = 0;
  }

}
