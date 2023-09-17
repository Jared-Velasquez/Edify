import { Subscription } from 'rxjs';
import { LoginService } from './../../services/login.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpStatusCode } from '@angular/common/http';
import { Store, USER_RUNTIME_CHECKS } from '@ngrx/store';
import { AppState } from 'src/app/store/models/edifyState';
import { UserActionTypes } from 'src/app/store/models/actionTypes';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  badCredentials: boolean;
  missingUsername: boolean;
  badUsername: boolean;
  missingPassword: boolean;
  badPassword: boolean;
  loginSubscription: Subscription;

  constructor(
    private router: Router, 
    private loginService: LoginService,
  ) { 
    this.badCredentials = false;
    this.missingUsername = false;
    this.badUsername = false;
    this.missingPassword = false;
    this.badPassword = false;
    this.loginSubscription = Subscription.EMPTY;
  }

  ngOnInit(): void {

  }

  onLogin(username: string, password: string) {
    if (!username) this.missingUsername = true; else this.missingUsername = false;
    if (!password) this.missingPassword = true; else this.missingPassword = false;

    // Check username
    const usernameExpression = new RegExp("^[a-z0-9]{2,15}$");
    if (!usernameExpression.test(username)) this.badUsername = true; else this.badUsername = false;
    // Check password
    const passwordExpression = new RegExp("^[a-zA-Z0-9!@#$%^&*().,]{6,}$");
    if (!passwordExpression.test(password)) this.badPassword = true; else this.badPassword = false;

    if (this.missingUsername || this.missingPassword || this.badUsername || this.badPassword) return;
    this.loginSubscription = this.loginService.login(username, password).subscribe({
      next: (response) => {
        if (!response) {
          this.badCredentials = true;
          return;
        }
  
        this.badCredentials = false;
        this.router.navigate(['dashboard']);
      },
      error: (error) => {
        console.log(error.status);
        if (error.status === HttpStatusCode.Forbidden || error.status === HttpStatusCode.Unauthorized)
          this.badCredentials = true;
      }
    })
  }

  ngOnDestroy(): void {
      if (this.loginSubscription)
      this.loginSubscription.unsubscribe();
  }
}
