import { Component } from '@angular/core';
import { navLinkOptions, NavLinksInterface } from 'src/constants';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  animations: []
})
export class NavbarComponent {
  navElements: NavLinksInterface[] = navLinkOptions;
  expanded: boolean = true;
}
