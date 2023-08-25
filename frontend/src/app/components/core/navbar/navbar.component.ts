import { Component, OnInit } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { navLinkOptions, NavLinksInterface } from 'src/constants';
import { expand, collapse } from 'src/app/store/actions/navbar.actions';
import { AppState } from 'src/app/store/models/edifyState'; 
import { expandedSelector } from 'src/app/store/selectors/stateSelectors';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  animations: [],
})
export class NavbarComponent implements OnInit {
  navElements: NavLinksInterface[] = navLinkOptions;
  expanded: boolean;

  constructor(private store: Store<{ state: { expanded: boolean } }>) {
    this.expanded = true;
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
}
