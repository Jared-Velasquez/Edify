import { Component, OnInit, OnDestroy } from '@angular/core';
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

  constructor(private courseService: CoursesService) {
    this.announcements = [];
    this.announcementsSubscription = Subscription.EMPTY;
  }

  ngOnInit() {
    this.announcementsSubscription = this.courseService.getAnnouncements(3).subscribe({
      next: (response) => {
        this.announcements = response;
        console.log(this.announcements);
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