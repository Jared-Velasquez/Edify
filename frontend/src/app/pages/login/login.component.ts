import { LoginService } from './../../services/login.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  badCredentials: boolean;

  constructor(
    private router: Router, 
    private loginService: LoginService,
  ) { 
    this.badCredentials = false;
  }

  ngOnInit(): void {

  }

  onLogin(username: string, password: string) {
    this.loginService.login(username, password).subscribe({
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
}
