import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SocketClientService} from './socket-client.service';
import {first} from 'rxjs/operators';
import {Message} from "../model/Message";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private socketClient: SocketClientService) {
  }

  findAll(): Observable<Message[]> {
    return this.socketClient
      .onMessage('/topic/chat/get')
      .pipe(first());
  }

  save(content: string, userId: number) {
    return this.socketClient.send('/topic/chat', new Message(content, userId));
  }

  onPost(): Observable<Message> {
    return this.socketClient.onMessage('/topic/chat/sent').pipe();
  }

}
