import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title: string = 'frontend';
  urlPath: string;
  showNavbar: boolean;

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {
    this.urlPath = "";
    this.showNavbar = false;
    router.events.pipe(filter(event => event instanceof NavigationEnd))
    .subscribe((event) => {
      const routerEvent: NavigationEnd = event as NavigationEnd;
      console.log("AppComponent: " + ((routerEvent.url === "/login") ? false : true))
      this.showNavbar = ((routerEvent.url === "/login") ? false : true);
    })
  }

  ngOnInit() {

  }
}
