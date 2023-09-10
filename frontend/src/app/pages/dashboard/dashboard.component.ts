import { Component, OnDestroy, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { Role } from 'src/app/models/edifyModels';
import { User, UserEmpty } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';
import { AppState } from 'src/app/store/models/edifyState';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  userSubscription: Subscription
  user: User;
  role: string;
  textFinished: boolean;

  constructor(private store: Store<AppState>) {
    this.userSubscription = Subscription.EMPTY;
    this.user = UserEmpty;
    this.role = "";
    this.textFinished = false;
  }

  ngOnInit() {
    this.userSubscription = this.store.select('user').subscribe((data) => {
      this.user = data.user;
    });
  }

  ngOnDestroy() {
    if (this.userSubscription)
      this.userSubscription.unsubscribe();
  }

  getRole(role: string): string {
    if (Role.TEACHER === role)
      return "Teacher";
    else
      return "Student";
  }

  onTextFinished(event: boolean) {
    if (event) {
      console.log("Text finished")
      this.textFinished = true;
    }
  }
}