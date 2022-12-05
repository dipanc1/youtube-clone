import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommentsService } from '../comments.service';
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

  constructor(private userService: UserService, private commentsService: CommentsService, private matSnackBar: MatSnackBar) {
    this.commentsForm = new FormGroup({
      comment: new FormControl('comment'),
    });
  }

  ngOnInit(): void {
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
    })

  }

}
