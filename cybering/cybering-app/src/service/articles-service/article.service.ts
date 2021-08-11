import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Article } from 'src/app/interfaces/article';
import { Observable } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

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
}
