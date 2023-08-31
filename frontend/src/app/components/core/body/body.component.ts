import { Component, Input, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { filter, Subscription } from 'rxjs';
import { AppState } from 'src/app/store/models/edifyState';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.scss'],
})
export class BodyComponent implements OnInit {
  @Input() showNavbar: boolean;
  navbarExpanded: boolean;
  navbarSubscription: Subscription;

  constructor(private store: Store<{ state: AppState }>) {
    this.navbarExpanded = true;
    this.showNavbar = true;
    this.navbarSubscription = Subscription.EMPTY;
  }

  ngOnInit(): void {
    this.store.select('state').subscribe((data) => {
      this.navbarExpanded = data.navbar.expanded;
    })
  }

  ngOnDestroy(): void {
    if (this.navbarSubscription)
      this.navbarSubscription.unsubscribe();
  }

  bodyStyles(): string {
    if (!this.showNavbar)
      return 'app-body-navbar-hidden';
    else if (this.navbarExpanded)
      return 'app-body-navbar-expanded';
    else
      return 'app-body-navbar-collapsed';
  }
}
