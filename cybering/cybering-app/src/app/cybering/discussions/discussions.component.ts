import { Component, OnInit } from '@angular/core';
import { Discussion } from 'src/app/interfaces/discussion';
import { CookieService } from 'ngx-cookie-service';
import { DiscussionService } from 'src/service/discussions-service/discussion.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-discussions',
  templateUrl: './discussions.component.html',
  styleUrls: ['./discussions.component.css']
})
export class DiscussionsComponent implements OnInit {

  discussionsExist: number = 0;
  discussionsArray !: Discussion[];
  selectedDiscussion: number = 0;
  isSelectedArray: boolean[] = [];
  discussionForm = this.fb.group({
    discussionReply: ['']
  });
  replySuccess: number = 0;
  constructor(
    private cookieService: CookieService,
    private discussionService: DiscussionService,
    private fb: FormBuilder
  ) {
    this.discussionService.getDiscussionsArray(this.cookieService.get('ST_TOKEN')).subscribe(
      result => {
        this.discussionsArray = result;
        if (result === null) {
          this.discussionsExist = -1;
        } else {
          this.discussionsExist = 1;
          for (let discussion of this.discussionsArray) {
            this.isSelectedArray.push(false);
          }
          this.isSelectedArray[0] = true;
        }
      }
    )
  }

  onSelect(index: number) {
    this.isSelectedArray[this.selectedDiscussion] = false;
    this.selectedDiscussion = index;
    this.isSelectedArray[this.selectedDiscussion] = true;
  }

  onPost() {
    this.discussionForm = this.fb.group({
      discussionReply: [this.discussionForm.controls.discussionReply.value, Validators.required]
    });

    if (this.discussionForm.invalid)
      return;

    this.discussionService.replyToDiscussion(this.cookieService.get('ST_TOKEN'),
      this.discussionsArray[this.selectedDiscussion],
      this.discussionForm.controls.discussionReply.value).subscribe(
        result => {
          if (result.data === 'failed') {
            this.replySuccess = -1;
          } else {
            this.replySuccess = 1;
            window.location.reload();
          }
          this.discussionForm = this.fb.group({
            discussionReply: ['']
          });
        }
      )
  }

  ngOnInit(): void {
  }

}
