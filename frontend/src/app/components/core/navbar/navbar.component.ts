import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { NavLinksInterface } from 'src/constants';
import { expand, collapse, toggle } from 'src/app/store/actions/navbar.actions';
import { AppState } from 'src/app/store/models/edifyState'; 
import { fadeAnimation } from '../../../animations/shared_animations';
import { CourseActionTypes, UserActionTypes } from 'src/app/store/models/actionTypes';
import { navLinkOptions } from './navLinkOptions';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  animations: [
    fadeAnimation,
  ],
})
export class NavbarComponent implements OnInit, OnDestroy {
  @Input() showNavbar: boolean;
  navElements: NavLinksInterface[];
  expanded: boolean;
  navbarSubscription: Subscription;
  coursesSubscription: Subscription;
  isNavbarLoading: boolean;

  constructor(private store: Store<AppState>) {
    this.expanded = true;
    this.showNavbar = false;
    this.navElements = navLinkOptions([]);
    this.navbarSubscription = Subscription.EMPTY;
    this.coursesSubscription = Subscription.EMPTY;
    this.isNavbarLoading = false;
  }

  ngOnInit(): void {
    if (this.showNavbar) {
      this.store.dispatch({ type: CourseActionTypes.GetCourses });
      this.store.dispatch({ type: CourseActionTypes.GetScores });
      this.store.dispatch({ type: UserActionTypes.GetUser });

      this.navbarSubscription = this.store.select('navbar').subscribe((data) => {
        this.expanded = data.expanded;
      });

      this.coursesSubscription = this.store.select('course').subscribe((data) => {
        this.navElements = navLinkOptions(data.courses);
      })
    }
  }

  ngOnDestroy(): void {
    if (this.navbarSubscription)
      this.navbarSubscription.unsubscribe();
    if (this.coursesSubscription)
      this.coursesSubscription.unsubscribe();
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
