import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from '../app-routing.module';
import { AccountComponent } from './account/account.component';
import { CalendarComponent } from './calendar/calendar.component';
import { CourseComponent } from './course/course.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginComponent } from './login/login.component';
import { SettingsComponent } from './settings/settings.component';

@NgModule({
  declarations: [
    AccountComponent,
    CalendarComponent,
    CourseComponent,
    DashboardComponent,
    LandingPageComponent,
    LoginComponent,
    SettingsComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    AppRoutingModule,
    BrowserAnimationsModule,
  ],
  exports: [
    AccountComponent,
    CalendarComponent,
    CourseComponent,
    DashboardComponent,
    LandingPageComponent,
    LoginComponent,
    SettingsComponent,
  ],
})
export class PagesModule { }