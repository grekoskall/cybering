import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Article } from 'src/app/interfaces/article';
import { Observable } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { SimpleString } from 'src/app/interfaces/simplestring';
import { ArticleReply } from 'src/app/interfaces/articlereply';

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
