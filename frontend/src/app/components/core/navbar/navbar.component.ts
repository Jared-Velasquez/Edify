import { Component } from '@angular/core';
import { navLinks, NavLinks } from 'src/constants';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  navElements: NavLinks[] = navLinks;
}
