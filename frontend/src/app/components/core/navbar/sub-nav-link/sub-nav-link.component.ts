import { NavLinksInterface } from 'src/constants';
import { Component, Input } from '@angular/core';
import { trigger, state, transition, animate, style } from '@angular/animations';

@Component({
  selector: 'app-sub-nav-link',
  templateUrl: './sub-nav-link.component.html',
  styleUrls: ['./sub-nav-link.component.scss'],
  animations: [
    trigger('revealSubnav', [
      state('show', style({
        height: '*',
      })),
      state('hide', style({
        height: '0',
        overflow: 'hidden',
      })),
      transition('show => hide', [style({overflow: 'hidden'}), 
        animate('200ms ease-in-out', style({
          height: '0',
        })),
      ]),
      transition('hide => show', [ 
        animate('200ms ease-in-out', style({
          height: '*',
        })),
      ]),
    ])
  ],
})
export class SubNavLinkComponent {
  @Input() items: NavLinksInterface[] | undefined;
  @Input() navbarExpanded: boolean;
  @Input() subExpanded: boolean;
  isNestedSubLinksExpanded: boolean;

  constructor() {
    this.items = [];
    this.navbarExpanded = true;
    this.subExpanded = false;
    this.isNestedSubLinksExpanded = false;
  }

  toggleNestedSubLinksExpanded() {
    if (this.navbarExpanded) {
      this.isNestedSubLinksExpanded = !this.isNestedSubLinksExpanded;
    }
  }
}
