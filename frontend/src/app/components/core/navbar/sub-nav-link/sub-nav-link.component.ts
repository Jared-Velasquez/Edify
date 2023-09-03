import { NavLinksInterface } from 'src/constants';
import { Component, Input } from '@angular/core';
import { trigger, state, transition, animate, style } from '@angular/animations';
import { hoverAnimation, hoverChildAnimation } from 'src/app/animations/shared_animations';

@Component({
  selector: 'app-sub-nav-link',
  templateUrl: './sub-nav-link.component.html',
  styleUrls: ['./sub-nav-link.component.scss'],
  animations: [
    hoverAnimation,
    hoverChildAnimation,
    trigger('revealSubnav', [
      state('show', style({
        height: '*',
        overflow: 'hidden',
      })),
      state('hide', style({
        height: '0',
        overflow: 'hidden',
      })),
      transition('show => hide', [
        style({
          overflow: 'hidden',
        }), 
        animate('400ms ease-in-out', style({
          height: '0',
        })),
      ]),
      transition('hide => show', [ 
        style({
          overflow: 'hidden',
        }),
        animate('400ms ease-in-out', style({
          height: '*',
        })),
      ]),
      transition('void <=> *', animate(0)),
    ])
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
