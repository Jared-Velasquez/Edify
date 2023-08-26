import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  urlPath: string;

  constructor(private activatedRoute: ActivatedRoute) {
    this.urlPath = "";
  }

  ngOnInit() {
    this.activatedRoute.url.subscribe(data => {
      this.urlPath = data[0].path;
      console.log(this.urlPath);
    });
  }
}
