import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, NavigationStart, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { fadeAnimation } from 'src/app/animations/shared_animations';
import { AnnouncementUnitResponse } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';
import { trigger, style, transition, animate, keyframes, query, stagger } from '@angular/animations';

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css'],
  animations: [
    fadeAnimation,
    trigger('announcementsAnimation', [
      transition('* => *', [
        query(':enter', style({ opacity: 0 }), { optional: true }),
        query(':enter', stagger('500ms', [
          animate('750ms ease-in', keyframes([
            style({ opacity: 0, transform: 'translateY(100px)', offset: 0 }),
            style({ opacity: .25, transform: 'translateY(50px)', offset: 0.25 }),
            style({ opacity: .5, transform: 'translateY(25px)', offset: 0.5 }),
            style({ opacity: .75, transform: 'translateY(10px)', offset: 0.75 }),
            style({ opacity: 1, transform: 'translateY(0)', offset: 1 }),
          ]))
        ]), { optional: true })
      ])
    ]),
  ],
})
export class AnnouncementsComponent implements OnInit, OnDestroy {
  announcements: AnnouncementUnitResponse[];
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