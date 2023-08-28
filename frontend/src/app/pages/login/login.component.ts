import { LoginService } from './../../services/login.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private loginService: LoginService) { 
  }

  ngOnInit(): void {

  }

  onLogin(username: string, password: string) {
    console.log('Logging in');
    this.loginService.login(username, password);
  }
}
