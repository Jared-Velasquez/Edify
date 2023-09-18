import { Component, Input } from '@angular/core';
import { Course } from 'src/app/models';
import { CourseEmpty } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';

@Component({
  selector: 'app-course-search',
  templateUrl: './course-search.component.html',
  styleUrls: ['./course-search.component.css']
})
export class CourseSearchComponent {
  @Input() course: Course;
  courseSelected: boolean;

  constructor(private courseService: CoursesService) {
    this.course = CourseEmpty;
    this.courseSelected = false;
  }

  onSelectCourse() {
    this.courseService.addStudentToCourse(this.course.courseId).subscribe((data) => {
      this.courseSelected = true;
    });
  }
}
