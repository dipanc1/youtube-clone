import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { CommentDto } from '../comment-dto';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private httpClient: HttpClient) { }

  postComment(commentDto: any, videoId: string): Observable<any> {
    return this.httpClient.post<any>(environment.backendUrl + videoId + "/comment", commentDto)
  }

  getAllComments(videoId: string):Observable<Array<CommentDto>> {
    return this.httpClient.get<CommentDto[]>(environment.backendUrl + videoId + "/comment");
  }
}
