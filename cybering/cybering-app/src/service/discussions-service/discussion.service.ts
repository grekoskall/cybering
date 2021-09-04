import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Discussion, DiscussionReply} from 'src/app/interfaces/discussion';
import { catchError, retry } from 'rxjs/operators';
import { SimpleString } from 'src/app/interfaces/simplestring';

@Injectable({
  providedIn: 'root'
})
export class DiscussionService extends BaseService {

  constructor(private http : HttpClient) {
    super();
  }

  getDiscussionsArray(cookie : string) : Observable<Discussion[]> {
    return this.http.post<Discussion[]>(
      this.extendurl('cybering/discussions'), 
      null,
      {headers: {
        "Cookies": cookie,
        "action": "discussion-array"
      }}
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

  replyToDiscussion(
    cookie : string, 
    discussion : Discussion, 
    reply : string) : Observable<SimpleString> {
      let discussionReply = new DiscussionReply();
      discussionReply.id = discussion.id;
      discussionReply.message = reply;
      return this.http.post<SimpleString>(
      this.extendurl('cybering/discussions'), 
      discussionReply,
      {headers: {
        "Cookies": cookie,
        "action": "discussion-reply"
      }}
    ).pipe(
      retry(2),
      catchError(this.handleError)
    );
  }

}
