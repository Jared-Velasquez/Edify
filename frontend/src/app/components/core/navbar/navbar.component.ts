import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { Observable, Subscription } from 'rxjs';
import { navLinkOptions, NavLinksInterface } from 'src/constants';
import { expand, collapse, toggle } from 'src/app/store/actions/navbar.actions';
import { AppState } from 'src/app/store/models/edifyState'; 
import { expandedSelector } from 'src/app/store/selectors/stateSelectors';
import { trigger, transition, style, animate, state } from '@angular/animations';
import { fadeAnimation } from './animations/shared_animations';
import { CoursesService } from 'src/app/services/courses.service';
import { CourseBasicResponse } from 'src/app/models/httpResponseModels';

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
export class NavbarComponent implements OnInit, OnDestroy {
  @Input() showNavbar: boolean;
  navElements: NavLinksInterface[] = navLinkOptions;
  expanded: boolean;
  navbarSubscription: Subscription;

  constructor(private store: Store<{ state: AppState }>, private courseService: CoursesService) {
    this.expanded = true;
    this.showNavbar = true;
    this.navbarSubscription = Subscription.EMPTY;
  }

  ngOnInit(): void {
    this.navbarSubscription = this.store.select('state').subscribe((data) => {
      this.expanded = data.navbar.expanded;
    })
  }

  ngOnDestroy(): void {
    if (this.navbarSubscription)
      this.navbarSubscription.unsubscribe();
  }

  expandNavbar() {
    console.log("expanding");
    this.store.dispatch(expand());
  }

  collapseNavbar() {
    console.log("collapsing");
    this.store.dispatch(collapse());
  }

  toggleNavbar() {
    console.log("toggling");
    this.store.dispatch(toggle());
  }
}
