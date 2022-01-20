import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatePipe} from '@angular/common';

const AUTH_API = 'http://localhost:9090/api/auth/';
const httpOptionsJson = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private datePipe: DatePipe) {
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {username, password}, httpOptionsJson);
  }

  logout(userId: number, token: string | null) {
    return this.http.post(AUTH_API + 'logout', {userId, token}, httpOptionsJson);
  }

  refreshToken(refreshToken: string) {
    return this.http.post(AUTH_API + 'refreshToken', {refreshToken}, httpOptionsJson);
  }
}
