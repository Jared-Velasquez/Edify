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
    this.showNavbar = true;
    router.events.pipe(filter(event => event instanceof NavigationEnd))
    .subscribe((event) => {
      const routerEvent: NavigationEnd = event as NavigationEnd;
      console.log(routerEvent);
      this.showNavbar = ((routerEvent.url === '/login') ? false : true);
      console.log("After subscribe: " + this.showNavbar);
    })
  }

  ngOnInit() {

  }
}
