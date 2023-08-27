import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.scss'],
})
export class BodyComponent implements OnInit {
  navbarExpanded: boolean;

  constructor(private store: Store<{ state: { expanded: boolean } }>) {
    this.navbarExpanded = true;
  }

  ngOnInit(): void {
    this.store.subscribe(data => {
      this.navbarExpanded = data.state.expanded;
    });
  }
}
