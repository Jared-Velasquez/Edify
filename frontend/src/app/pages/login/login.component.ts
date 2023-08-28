import { LoginService } from './../../services/login.service';
import { Component, OnInit } from '@angular/core';
import { HttpStatusCode } from '@angular/common/http';
import { Router } from '@angular/router';
import { JWTInterface } from 'src/app/models/jwt';

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
    this.loginService.login(username, password).subscribe((response) => {
      console.log(response);
      if (!response) {
        this.badCredentials = true;
        return;
      }

      this.badCredentials = false;
      this.router.navigate(['dashboard']);
    });
  }
}
