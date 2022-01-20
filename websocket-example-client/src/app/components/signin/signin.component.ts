import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {TokenStorageService} from "../../service/token-storage.service";

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  form: any = {
    username: null,
    password: null
  }
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  confirm = '';
  guard = null;
  username = '';

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getJwtToken()) {
      this.isLoggedIn = true;
      this.username = this.tokenStorage.getUser().username;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  onSubmit(): void {
    const {username, password} = this.form;

    this.authService.login(username, password).subscribe(
      data => {
        this.tokenStorage.saveJwtToken(data.token);
        this.tokenStorage.saveRefreshToken(data.refreshToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.router.navigate(['chat']);
      },
      err => {
        if (err.error.message === 'Bad credentials') {
          this.errorMessage = 'Niepoprawne dane uwierzytelniające';
        } else if (err.error.message === 'User is disabled') {
          this.errorMessage = 'Użytkownik zablokowany';
        } else {
          console.log(err)
          this.errorMessage = 'Błąd serwera';
        }
        this.isLoginFailed = true;
      }
    );
  }
}
