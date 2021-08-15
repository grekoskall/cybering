import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Article } from 'src/app/interfaces/article';
import { Observable } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { ArticleReply } from 'src/app/interfaces/articlereply';
import { ArticlePost } from 'src/app/interfaces/articlepost';

@Injectable({
  providedIn: 'root'
})
export class ArticleService extends BaseService {

  constructor(private http: HttpClient) {
    super();
  }

  getArticlesList(cookie: string): Observable<Object[]> {
    return this.http.post<Object[]>(
      this.extendurl('cybering/home-page'),
      null,
      {
        headers: {
          'Cookies': cookie,
          'action': "article-list"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  postArticle(cookie: string, text: string, media: string, url: string, categories: string): Observable<SimpleString> {
    let ap = new ArticlePost();
    ap.articleText = text;
    ap.articleMedia = media;
    ap.articleUrl = url;
    ap.articleCategories = categories;
    return this.http.post<SimpleString>(
      this.extendurl('cybering/home-page'),
      ap,
      {
        headers: {
          'Cookies': cookie,
          'action': "post-article"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  replyToArticle(cookie: string, ad: ArticleReply): Observable<SimpleString> {
    return this.http.post<SimpleString>(
      this.extendurl('cybering/home-page'),
      ad,
      {
        headers: {
          'Cookies': cookie,
          'action': "reply-article"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  changeInterestToArticle(cookie: string, id: SimpleString): Observable<SimpleString> {
    return this.http.post<SimpleString>(
      this.extendurl('cybering/home-page'),
      id,
      {
        headers: {
          'Cookies': cookie,
          'action': "interest-article"
        }
      }
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }
}
