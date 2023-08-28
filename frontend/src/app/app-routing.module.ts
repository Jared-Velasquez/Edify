import { LoginComponent } from './pages/login/login.component';
import { CourseComponent } from './pages/course/course.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './pages/account/account.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { authenticationGuard } from 'src/app/auth/authentication/authentication.guard';

const routes: Routes = [
  { path: 'account', component: AccountComponent, canActivate: [authenticationGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authenticationGuard] },
  { path: 'calendar', component: CalendarComponent, canActivate: [authenticationGuard] },
  { path: 'course/:id', component: CourseComponent, canActivate: [authenticationGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'settings', component: SettingsComponent, canActivate: [authenticationGuard] },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
