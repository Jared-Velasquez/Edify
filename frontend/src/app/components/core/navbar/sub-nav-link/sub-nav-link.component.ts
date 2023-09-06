import { NavLinksInterface } from 'src/constants';
import { Component, Input } from '@angular/core';
import { trigger, state, transition, animate, style } from '@angular/animations';
import { hoverAnimation, hoverChildAnimation, revealOptions } from 'src/app/animations/shared_animations';

@Component({
  selector: 'app-sub-nav-link',
  templateUrl: './sub-nav-link.component.html',
  styleUrls: ['./sub-nav-link.component.scss'],
  animations: [
    hoverAnimation,
    hoverChildAnimation,
    revealOptions,
  ],
})
export class SubNavLinkComponent {
  @Input() subnav: NavLinksInterface;
  @Input() navbarExpanded: boolean;
  @Input() subExpanded: boolean;
  isNestedSubLinksExpanded: boolean;
  isOver: boolean;

  constructor() {
    this.subnav = {
      name: "",
    };
    this.navbarExpanded = true;
    this.subExpanded = false;
    this.isNestedSubLinksExpanded = false;
    this.isOver = false;
  }

  toggleNestedSubLinksExpanded() {
    if (this.navbarExpanded) {
      this.isNestedSubLinksExpanded = !this.isNestedSubLinksExpanded;
    } else {
      this.isNestedSubLinksExpanded = false;
    }
  }
}
