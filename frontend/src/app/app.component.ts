import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {
  title: string = 'frontend';
  urlPath: string;
  showNavbar: boolean;
  routerSubscription: Subscription;

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {
    this.urlPath = "";
    this.showNavbar = false;
    this.routerSubscription = router.events.pipe(filter(event => event instanceof NavigationEnd))
    .subscribe((event) => {
      const routerEvent: NavigationEnd = event as NavigationEnd;
      this.showNavbar = ((routerEvent.url === "/login" || routerEvent.url === "/signup") ? false : true);
    })
  }

  ngOnDestroy() {
    if (this.routerSubscription)
      this.routerSubscription.unsubscribe();
  }
}
