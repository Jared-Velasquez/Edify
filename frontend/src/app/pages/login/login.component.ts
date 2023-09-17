import { Subscription } from 'rxjs';
import { LoginService } from './../../services/login.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpStatusCode } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/models/edifyState';
import { UserActionTypes } from 'src/app/store/models/actionTypes';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  badCredentials: boolean;
  loginSubscription: Subscription;

  constructor(
    private router: Router, 
    private loginService: LoginService,
  ) { 
    this.badCredentials = false;
    this.loginSubscription = Subscription.EMPTY;
  }

  ngOnInit(): void {

  }

  onLogin(username: string, password: string) {
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
