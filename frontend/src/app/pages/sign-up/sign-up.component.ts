import { HttpStatusCode } from '@angular/common/http';
import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DropDownMenuInterface } from 'src/app/models';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnDestroy {
  menu: DropDownMenuInterface;
  chosenRole: string;
  badCredentials: boolean;
  loginSubscription: Subscription;
  missingUsername: boolean;
  badUsername: boolean;
  missingPassword: boolean;
  badPassword: boolean;
  missingFirstName: boolean;
  missingLastName: boolean;

  constructor(private loginService: LoginService, private router: Router) {
    this.menu = {
      title: "Role",
      options: ["Student", "Teacher"],
    };
    this.chosenRole = "STUDENT";
    this.badCredentials = false;
    this.loginSubscription = Subscription.EMPTY;
    this.missingUsername = false;
    this.badUsername = false;
    this.missingPassword = false;
    this.badPassword = false;
    this.missingFirstName = false;
    this.missingLastName = false;
  }

  onSignup(username: string, password: string, firstName: string, lastName: string, role: string) {
    if (!username) this.missingUsername = true; else this.missingUsername = false;
    if (!password) this.missingPassword = true; else this.missingPassword = false;
    if (!firstName) this.missingFirstName = true; else this.missingFirstName = false;
    if (!lastName) this.missingLastName = true; else this.missingLastName = false;

    // Check username
    const usernameExpression = new RegExp("^[a-z0-9]{2,15}$");
    if (!usernameExpression.test(username)) this.badUsername = true; else this.badUsername = false;
    // Check password
    const passwordExpression = new RegExp("^[a-zA-Z0-9!@#$%^&*().,]{6,}$");
    if (!passwordExpression.test(password)) this.badPassword = true; else this.badPassword = false;

    if (this.missingUsername || this.missingPassword || this.missingFirstName || this.missingLastName || this.badUsername || this.badPassword) return;
    this.loginSubscription = this.loginService.signup(username, password, firstName, lastName, role).subscribe({
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

  onChosenRole(role: string) {
    if (role === "Teacher")
      this.chosenRole = "TEACHER";
    else
      this.chosenRole = "STUDENT";
  }

  ngOnDestroy(): void {
      if (this.loginSubscription)
        this.loginSubscription.unsubscribe();
  }
}
