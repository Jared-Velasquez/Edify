import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { forkJoin, map, Subscription, switchMap } from 'rxjs';
import { fadeDelayedAnimation, listAnimation } from 'src/app/animations/shared_animations';
import { Announcement, Course } from 'src/app/models';
import { CourseEmpty, Teacher, TeacherEmpty } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';
import { AppState } from 'src/app/store/models/edifyState';

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css'],
  animations: [
    fadeDelayedAnimation,
    listAnimation,
  ],
})
export class AnnouncementsComponent implements OnInit, OnDestroy {
  announcements: Announcement[];
  routeSubscription: Subscription;
  courseId: number;
  isLoading: boolean;
  course: Course;
  teacher: Teacher;

  constructor(private courseService: CoursesService, private route: ActivatedRoute, private store: Store<AppState>) {
    this.announcements = [];
    this.routeSubscription = Subscription.EMPTY;
    this.courseId = 0;
    this.isLoading = true;
    this.course = CourseEmpty;
    this.teacher = TeacherEmpty;
  }

  ngOnInit() {
    this.routeSubscription = this.route.paramMap.pipe(
      switchMap(response => {
        const id: string | null = response.get('id');
        const courseId = id ? parseInt(id) : 0;
        return this.courseService.getAnnouncements(courseId).pipe(
          map(announcements => ({
            announcements,
            courseId,
          }))
        );
      }),
      switchMap(response => {
        return this.store.select('navbar').pipe(
          map((navbarData) => {
            const courseOptional: Course | undefined = navbarData.courses.find(course => course.courseId === response.courseId);
            if (courseOptional) {
              const course: Course = courseOptional;
              return {
                course,
                ...response,
              }
            } else {
              const course: Course = CourseEmpty;
              return {
                course,
                ...response,
              }
            }
          })
        );
      }),
      switchMap(response => {
        return this.courseService.getTeacherOfCourse(response.courseId).pipe(
          map(teacher => ({
            teacher,
            ...response,
          }))
        );
      })
    ).subscribe((response) => {
      this.courseId = response.courseId;
      this.announcements = response.announcements;
      this.course = response.course;
      this.teacher = response.teacher;
    });
  }

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}