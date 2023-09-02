import { Component, Input, HostBinding, OnChanges, SimpleChanges } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { NavLinksInterface } from 'src/constants';
import { fadeAnimation, hoverAnimation, hoverChildAnimation } from '../animations/shared_animations';

@Component({
  selector: 'app-nav-link',
  templateUrl: './nav-link.component.html',
  styleUrls: ['./nav-link.component.scss'],
  animations: [
    hoverAnimation,
    fadeAnimation,
    hoverChildAnimation,
  ],
})
export class NavLinkComponent implements OnChanges {
  @Input() navIcon: string | undefined;
  @Input() navLink: string | undefined;
  @Input() navName: string;
  @Input() expanded: boolean; // Is navbar expanded
  @Input() subitems: NavLinksInterface[] | undefined; // Sublinks for this parent
  isOver: boolean; // Is cursor hovering over element
  isSubLinksExpanded: boolean; // Are the sublinks shown?

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
