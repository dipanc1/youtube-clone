import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadVideoComponent } from '../upload-video/upload-video.component';
import { UploadVideoResponse } from '../upload-video/UploadVideoResponse';
import { VideoDto } from '../video-dto';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  uploadVideo(fileEntry: File): Observable<UploadVideoResponse> {

    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)

    // HTTP Post call to upload the video
    return this.httpClient.post<UploadVideoResponse>(environment.backendUrl, formData);

  }

  uploadThmbnail(fileEntry: File, videoId: string): Observable<string> {

    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)
    formData.append('videoId', videoId);

    // HTTP Post call to upload the thumbnail
    return this.httpClient.post(environment.backendUrl + "thumbnail", formData, {
      responseType: 'text'
    });
  }

  getVideo(videoId: string): Observable<VideoDto> {
    return this.httpClient.get<VideoDto>(environment.backendUrl + videoId);
  }


  saveVideo(videoMetaData: VideoDto): Observable<VideoDto> {
    // HTTP Post call to upload the thumbnail
    return this.httpClient.put<VideoDto>(environment.backendUrl, videoMetaData);
  }

  getAllVideos(): Observable<Array<VideoDto>> {
    return this.httpClient.get<Array<VideoDto>>(environment.backendUrl)
  }

  likeVideo(videoId: string):Observable<VideoDto> {
    return this.httpClient.post<VideoDto>(environment.backendUrl + videoId + "/like", null);
  }

  dislikeVideo(videoId: string):Observable<VideoDto> {
    return this.httpClient.post<VideoDto>(environment.backendUrl + videoId + "/dislike", null);
  }
}
