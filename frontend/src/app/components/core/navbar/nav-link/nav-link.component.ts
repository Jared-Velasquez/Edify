import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-nav-link',
  templateUrl: './nav-link.component.html',
  styleUrls: ['./nav-link.component.scss']
})
export class NavLinkComponent {
  @Input() navIcon: string;
  @Input() navLink: string | undefined;
  @Input() navName: string;

  hoverBox: boolean;

  constructor() {
    this.navIcon = "";
    this.navLink = "";
    this.navName = "";
    this.hoverBox = false;
  }

  test(): void {
    console.log(`Mouse over ${this.navLink}`);
  }

  test2(): void {
    console.log(`Mouse out ${this.navLink}`);
  }
}
