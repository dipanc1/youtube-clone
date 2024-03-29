import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommentDto } from '../comment-dto';
import { CommentsService } from '../services/comments.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  @Input()
  videoId: string = '';
  commentsForm: FormGroup;
  commentsDto: CommentDto[] = [];

  constructor(private userService: UserService, private commentsService: CommentsService, private matSnackBar: MatSnackBar) {
    this.commentsForm = new FormGroup({
      comment: new FormControl('comment'),
    });
  }

  ngOnInit(): void {
    this.getComments();
  }

  postComment() {
    const comment = this.commentsForm.get('comment')?.value;

    const commentDto = {
      "commentText": comment,
      "authorId": this.userService.getUserId()
    }

    this.commentsService.postComment(commentDto, this.videoId).subscribe(() => {
      this.matSnackBar.open("Comment Posted Successfully", "OK");
      this.commentsForm.get('comment')?.reset();
      this.getComments();
    });
  }

  getComments() {
    this.commentsService.getAllComments(this.videoId).subscribe(data => {
      this.commentsDto = data;
    });
  }

}
