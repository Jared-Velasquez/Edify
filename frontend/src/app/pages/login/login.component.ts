import { LoginService } from './../../services/login.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  badCredentials: boolean;

  constructor(private loginService: LoginService) { 
    this.badCredentials = false;
  }

  ngOnInit(): void {

  }

  onLogin(username: string, password: string) {
    this.loginService.login(username, password).subscribe({
      next: (response) => {
        this.badCredentials = false;
        console.log(response);
      },
      error: (error) => {
        console.log(error);
        if (error.status === HttpStatusCode.Forbidden)
          this.badCredentials = true;
      }
    });
  }
}
