import { Component, Input, HostBinding, OnChanges, SimpleChanges } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { NavLinksInterface } from 'src/constants';
import { fadeAnimation } from '../animations/shared_animations';

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
    fadeAnimation,
  ],
})
export class NavLinkComponent implements OnChanges {
  @Input() navIcon: string | undefined;
  @Input() navLink: string | undefined;
  @Input() navName: string;
  @Input() expanded: boolean;
  @Input() subitems: NavLinksInterface[] | undefined;
  isOver: boolean;
  isSubLinksExpanded: boolean;

  constructor() {
    this.navIcon = "";
    this.navLink = "";
    this.navName = "";
    this.expanded = true;
    this.subitems = [];
    this.isOver = false;
    this.isSubLinksExpanded = false;
  }

  ngOnChanges(changes: SimpleChanges) {
    // If the navbar 'expanded' property changes, and the previous value was true,
    // close the sublinks
    if (changes['expanded'] && changes['expanded'].previousValue) {
      this.isSubLinksExpanded = false;
    }
  }

  toggleExpandSubLinks() {
    this.isSubLinksExpanded = this.expanded && !this.isSubLinksExpanded;
  }
}
