import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { map, of } from 'rxjs';
import { CourseBasicResponse, CourseBasicUnitResponse } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  constructor(private courseService: CoursesService) {

  }

  ngOnInit() {

  }
}