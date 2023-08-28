import { LoginService } from './../../services/login.service';
import { Component, OnInit } from '@angular/core';
import { HttpStatusCode } from '@angular/common/http';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/services/jwt.service';
import { TokenResponse } from 'src/app/models/httpresponses';
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
    private jwtService: JwtService
  ) { 
    this.badCredentials = false;
  }

  ngOnInit(): void {

  }

  onLogin(username: string, password: string) {
    this.loginService.login(username, password).subscribe({
      next: (response) => {
        this.badCredentials = false;
        if (!response.body)
          return;
        let decodedToken: JWTInterface | undefined = this.jwtService.getDecodedAccessToken(response.body.token);
        if (!decodedToken)
          return;
        console.log(decodedToken);
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        if (error.status === HttpStatusCode.Forbidden)
          this.badCredentials = true;
      }
    });
  }
}
