import { NavLinksInterface } from 'src/constants';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-sub-nav-link',
  templateUrl: './sub-nav-link.component.html',
  styleUrls: ['./sub-nav-link.component.css']
})
export class SubNavLinkComponent {
  @Input() items: NavLinksInterface[] | undefined;
  @Input() subExpanded: boolean;

  constructor() {
    this.items = [];
    this.subExpanded = false;
  }
}
