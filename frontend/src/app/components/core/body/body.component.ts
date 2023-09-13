import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
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
  routerSubscription: Subscription;
  onDashboard: boolean;
  onCourseHome: boolean;

  constructor(private store: Store<AppState>, private activatedRoute: ActivatedRoute, private router: Router) {
    this.navbarExpanded = true;
    this.showNavbar = true;
    this.navbarSubscription = Subscription.EMPTY;
    this.routerSubscription = Subscription.EMPTY;
    this.onDashboard = true;
    this.onCourseHome = true;
  }

  ngOnInit(): void {
    this.navbarSubscription = this.store.select('navbar').subscribe((data) => {
      this.navbarExpanded = data.expanded;
    });

    this.routerSubscription = this.router.events.pipe(filter(event => event instanceof NavigationEnd))
    .subscribe((event) => {
      const routerEvent: NavigationEnd = event as NavigationEnd;
      this.onDashboard = ((routerEvent.url === "/dashboard" || routerEvent.url === '/') ? true : false);
      this.onCourseHome = ((routerEvent.url.indexOf("/course") >= 0 && (routerEvent.url.split('/').length - 1 === 2)) ? true : false);
    });
  }

  ngOnDestroy(): void {
    if (this.navbarSubscription)
      this.navbarSubscription.unsubscribe();
    if (this.routerSubscription)
      this.routerSubscription.unsubscribe();
  }

  bodyStyles(): string {
    if (!this.showNavbar)
      return 'app-body-navbar-hidden';
    else {
      if (this.navbarExpanded) {
        if (this.onDashboard)
          return 'app-body-dashboard-navbar-expanded';
        else if (this.onCourseHome)
          return 'app-body-full-navbar-expanded';
        return 'app-body-navbar-expanded';
      } else {
        if (this.onDashboard)
          return 'app-body-full-navbar-collapsed';
        else if (this.onCourseHome)
          return 'app-body-full-navbar-collapsed';
        return 'app-body-navbar-collapsed';
      }
    }
  }
}
