import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-timeline-post',
  templateUrl: './timeline-post.component.html',
  styleUrls: ['./timeline-post.component.css']
})
export class TimelinePostComponent implements OnInit {
  showPostArticle: boolean = false;
  constructor() { }

  ngOnInit(): void {
  }

  postArticle():void {
    this.showPostArticle = !this.showPostArticle;
  }

}
