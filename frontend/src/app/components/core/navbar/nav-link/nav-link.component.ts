import { Component, Input, HostBinding } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-nav-link',
  templateUrl: './nav-link.component.html',
  styleUrls: ['./nav-link.component.scss'],
  animations: [
    trigger('hoverOnOff', [
      state('on', style({
        backgroundColor: '#fff',
      })),
      state('off', style({
      })),
      transition('on => off', [
        animate('0.05s')
      ]),
      transition('off => on', [
        animate('0.1s')
      ]),
    ]),
    trigger('hoverOnOffChild', [
      state('on', style({
        color: 'black',
      })),
      state('off', style({
      })),
      transition('on => off', [
        animate('0.05s')
      ]),
      transition('off => on', [
        animate('0.1s')
      ]),
    ]),
  ],
})
export class NavLinkComponent {
  @Input() navIcon: string;
  @Input() navLink: string | undefined;
  @Input() navName: string;

  isOver: boolean;

  constructor() {
    this.navIcon = "";
    this.navLink = "";
    this.navName = "";
    this.isOver = false;
  }
}
