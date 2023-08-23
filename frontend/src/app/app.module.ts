import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccountComponent } from './pages/account/account.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { CourseComponent } from './pages/course/course.component';
import { StoreModule } from '@ngrx/store';
import { LoginComponent } from './pages/login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SettingsComponent } from './pages/settings/settings.component';
import { CoreModule } from './components/core/core.module';

@NgModule({
  declarations: [
    AppComponent,
    AccountComponent,
    DashboardComponent,
    CalendarComponent,
    CourseComponent,
    LoginComponent,
    LandingPageComponent,
    SettingsComponent,
  ],
  imports: [
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    StoreModule.forRoot({}, {}),
    BrowserAnimationsModule,
    HttpClientModule,
    FontAwesomeModule,
    CoreModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
