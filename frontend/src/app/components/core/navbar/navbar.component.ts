import { Component, Input, OnInit } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { navLinkOptions, NavLinksInterface } from 'src/constants';
import { expand, collapse, toggle } from 'src/app/store/actions/navbar.actions';
import { AppState } from 'src/app/store/models/edifyState'; 
import { expandedSelector } from 'src/app/store/selectors/stateSelectors';
import { trigger, transition, style, animate, state } from '@angular/animations';
import { fadeAnimation } from './animations/shared_animations';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  animations: [
    trigger('revealNavbar', [
      state('show', style({
        width: '*',
      })),
      state('hide', style({
        width: '0',
        overflow: 'hidden',
      })),
      transition('show => hide', [style({overflow: 'hidden'}), 
        animate('200ms ease-in-out', style({
          width: '0',
        })),
      ]),
      transition('hide => show', [ 
        animate('200ms ease-in-out', style({
          width: '*',
        })),
      ]),
    ]),
    fadeAnimation,
  ],
})
export class NavbarComponent implements OnInit {
  @Input() showNavbar: boolean;
  navElements: NavLinksInterface[] = navLinkOptions;
  expanded: boolean;

  constructor(private store: Store<{ state: { expanded: boolean } }>) {
    this.expanded = true;
    this.showNavbar = true;
  }

  ngOnInit(): void {
    this.store.subscribe(data => {
      this.expanded = data.state.expanded;
    });
  }

  expandNavbar() {
    this.store.dispatch(expand());
  }

  collapseNavbar() {
    this.store.dispatch(collapse());
  }

  toggleNavbar() {
    this.store.dispatch(toggle());
  }
}
