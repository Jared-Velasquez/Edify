import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { fadeAnimation, listAnimation } from 'src/app/animations/shared_animations';
import { Announcement } from 'src/app/models';
import { CoursesService } from 'src/app/services/courses.service';

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css'],
  animations: [
    fadeAnimation,
    listAnimation,
  ],
})
export class AnnouncementsComponent implements OnInit, OnDestroy {
  announcements: Announcement[];
  announcementsSubscription: Subscription;
  routeSubscription: Subscription;
  courseId: number;
  isLoading: boolean;

  constructor(private courseService: CoursesService, private route: ActivatedRoute) {
    this.announcements = [];
    this.announcementsSubscription = Subscription.EMPTY;
    this.routeSubscription = Subscription.EMPTY;
    this.courseId = 0;
    this.isLoading = true;
  }

  ngOnInit() {
    this.routeSubscription = this.route.paramMap.subscribe((response) => {
      this.announcements = [];
      const id: string | null = response.get('id');
      this.courseId = id ? parseInt(id) : 0;

      this.announcementsSubscription = this.courseService.getAnnouncements(this.courseId).subscribe({
        next: (response) => {
          this.announcements = response;
        },
        error: (error) => {
          console.log(error);
          this.announcements = [];
        },
        complete: () => {
          this.isLoading = false;
        }
      })
    })  
  }

  ngOnDestroy() {
    if (this.announcementsSubscription)
      this.announcementsSubscription.unsubscribe();
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}