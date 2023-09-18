import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/models';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  courses: Course[];
  
  constructor() {
    this.courses = [];
  }

  ngOnInit() {
    
  }
}
