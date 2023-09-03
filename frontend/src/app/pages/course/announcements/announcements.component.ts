import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { AnnouncementUnitResponse } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})
export class AnnouncementsComponent implements OnInit, OnDestroy {
  announcements: AnnouncementUnitResponse[];
  announcementsSubscription: Subscription;
  courseId: number;

  constructor(private courseService: CoursesService, private route: ActivatedRoute) {
    this.announcements = [];
    this.announcementsSubscription = Subscription.EMPTY;
    this.courseId = 0;
  }

  ngOnInit() {
    const id: string | null = this.route.snapshot.paramMap.get('id');
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
      }
    })
  }

  ngOnDestroy() {
    if (this.announcementsSubscription)
      this.announcementsSubscription.unsubscribe();
  }
}