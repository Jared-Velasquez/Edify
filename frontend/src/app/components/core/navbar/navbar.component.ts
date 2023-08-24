import { Component } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { navLinkOptions, NavLinksInterface } from 'src/constants';
import { expand, collapse } from 'src/app/store/actions/navbar.actions';
import { AppState } from 'src/app/store/models/edifyState'; 
import { expandedSelector } from 'src/app/store/selectors/stateSelectors';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  animations: []
})
export class NavbarComponent {
  navElements: NavLinksInterface[] = navLinkOptions;
  expanded$: Observable<boolean>;

  constructor(private store: Store<AppState>) {
    this.expanded$ = this.store.pipe(select(expandedSelector));
  }

  expandNavbar() {
    console.log('expanding navbar');
    this.store.dispatch(expand());
    console.log(this.expanded$);
  }

  collapseNavbar() {
    console.log('collapsing navbar');
    this.store.dispatch(collapse());
    console.log(this.expanded$);
  }
}
