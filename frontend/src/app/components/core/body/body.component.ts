import { Component, Input, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { filter } from 'rxjs';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.scss'],
})
export class BodyComponent implements OnInit {
  @Input() showNavbar: boolean;
  navbarExpanded: boolean;

  constructor(private store: Store<{ state: { expanded: boolean } }>) {
    this.navbarExpanded = true;
    this.showNavbar = true;
  }

  ngOnInit(): void {
    this.store.subscribe(data => {
      this.navbarExpanded = data.state.expanded;
    });
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
