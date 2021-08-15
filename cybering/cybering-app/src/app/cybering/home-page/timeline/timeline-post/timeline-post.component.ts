import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from 'src/service/articles-service/article.service';

@Component({
  selector: 'app-timeline-post',
  templateUrl: './timeline-post.component.html',
  styleUrls: ['./timeline-post.component.css']
})
export class TimelinePostComponent implements OnInit {
  showPostArticle: boolean = false;
  postSuccess: number = 0;

  articleForm = this.fb.group({
    articleText: [''],
    articleMediaType: ['Embed Media'],
    articleMediaUrl: [''],
    articleCategories: ['']
  });

  constructor(
    private fb: FormBuilder,
    private cookieService: CookieService,
    private articleService: ArticleService
  ) { }

  ngOnInit(): void {
  }

  postArticle(): void {
    this.showPostArticle = !this.showPostArticle;
  }

  post(): void {
    this.articleForm = this.fb.group({
      articleText: [this.articleForm.controls.articleText.value, Validators.required],
      articleMediaType: [this.articleForm.controls.articleMediaType.value, Validators.required],
      articleMediaUrl: [this.articleForm.controls.articleMediaUrl.value, Validators.required],
      articleCategories: [this.articleForm.controls.articleCategories.value]
    });
    if (this.articleForm.invalid) {
      return;
    }
    this.articleService.postArticle(
      this.cookieService.get('ST_TOKEN'),
      this.articleForm.controls.articleText.value,
      this.articleForm.controls.articleMediaType.value,
      this.articleForm.controls.articleMediaUrl.value,
      this.articleForm.controls.articleCategories.value
    ).subscribe(
      result => {
        if (result.data === 'failed') {
          this.postSuccess = -1;
        } else {
          this.postSuccess = 1;
          this.postArticle();
          window.location.reload();
        }
        this.articleForm = this.fb.group({
          articleText: [''],
          articleMediaType: [''],
          articleMediaUrl: ['']
        });
      }
    );
  }
}
