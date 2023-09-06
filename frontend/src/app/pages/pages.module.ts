import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from '../app-routing.module';
import { AccountComponent } from './account/account.component';
import { CalendarComponent } from './calendar/calendar.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginComponent } from './login/login.component';
import { SettingsComponent } from './settings/settings.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ModulesComponent } from './course/modules/modules.component';
import { AssignmentsComponent } from './course/assignments/assignments.component';
import { AnnouncementsComponent } from './course/announcements/announcements.component';
import { SharedModule } from 'src/app/components/shared/shared.module';

@NgModule({
  declarations: [
    AccountComponent,
    CalendarComponent,
    DashboardComponent,
    LandingPageComponent,
    LoginComponent,
    SettingsComponent,
    PageNotFoundComponent,
    ModulesComponent,
    AssignmentsComponent,
    AnnouncementsComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule
  ],
  exports: [
    AccountComponent,
    CalendarComponent,
    DashboardComponent,
    LandingPageComponent,
    LoginComponent,
    SettingsComponent,
  ],
})
export class PagesModule { }
