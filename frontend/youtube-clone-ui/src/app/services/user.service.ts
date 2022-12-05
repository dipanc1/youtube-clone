import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userId: string = '';

  constructor(private httpClient: HttpClient) { }


  subscribeUser(userId: string): Observable<boolean> {
    return this.httpClient.post<boolean>(environment.backendUserUrl + "subscribe/" + userId, null);
  }

  unSubscribeUser(userId: string): Observable<boolean> {
    return this.httpClient.post<boolean>(environment.backendUserUrl + "unsubscribe/" + userId, null);
  }

  registerUser() {
    this.httpClient.get(environment.backendUserUrl + "register", { responseType: 'text' }).subscribe(data => {
      this.userId = data;
    });
  }

  getUserId(): string {
    return this.userId;
  }

}
