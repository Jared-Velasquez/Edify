import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Course } from 'src/app/models';
import { CoursesService } from 'src/app/services/courses.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit, OnDestroy {
  courseSubscription: Subscription;
  courses: Course[];
  
  constructor(private courseService: CoursesService) {
    this.courseSubscription = Subscription.EMPTY;
    this.courses = [];
  }

  ngOnInit() {
    this.courseSubscription = this.courseService.getUnenrolledCourses().subscribe((data) => {
      this.courses = data;
      console.log(this.courses);
    })
  }

  onSelectCourse(courseId: number) {
    this.courseService.addStudentToCourse(courseId).subscribe((data) => {
      console.log(data);
    });
  }

  ngOnDestroy() {
    if (this.courseSubscription)
      this.courseSubscription.unsubscribe();
  }
}
