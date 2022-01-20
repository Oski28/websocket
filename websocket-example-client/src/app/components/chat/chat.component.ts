import {Component, OnInit} from '@angular/core';
import {Subject, takeUntil} from "rxjs";
import {MessageService} from "../../service/message.service";
import {AbstractControl, FormBuilder, FormGroup} from "@angular/forms";
import {TokenStorageService} from "../../service/token-storage.service";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  messages: any[];
  private unsubscribeSubject: Subject<void> = new Subject<void>();
  form!: FormGroup;

  constructor(private service: MessageService, private formBuilder: FormBuilder,
              private authService: AuthService, private tokenStorage: TokenStorageService) {
    this.messages = [];
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        message: ['', []]
      }, {}
    );
    this.service.findAll().subscribe(messages => {
      console.log(messages);
      this.messages = messages;
    });
    this.service.onPost().pipe(takeUntil(this.unsubscribeSubject)).subscribe(message => {
      console.log(message)
      // @ts-ignore
      this.messages.push(message);
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }

  onSubmit() {
    this.service.save(this.form.controls["message"].value, this.tokenStorage.getUser().id);
    this.form.reset();
  }

  logout() {
    this.authService.logout(this.tokenStorage.getUser().id, this.tokenStorage.getJwtToken());
    this.tokenStorage.signOut();
    window.location.reload();
  }
}
