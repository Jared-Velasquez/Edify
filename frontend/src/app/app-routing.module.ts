import { LoginComponent } from './pages/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './pages/account/account.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { authenticationGuard } from 'src/app/auth/authentication/authentication.guard';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { AnnouncementsComponent } from './pages/course/announcements/announcements.component';
import { ModulesComponent } from './pages/course/modules/modules.component';
import { AssignmentsComponent } from './pages/course/assignments/assignments.component';
import { AssignmentComponent } from './pages/course/assignments/assignment-page/assignment-page.component';

const routes: Routes = [
  { path: 'account', component: AccountComponent, canActivate: [authenticationGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authenticationGuard] },
  { path: 'calendar', component: CalendarComponent, canActivate: [authenticationGuard] },
  { path: 'courses/:id/announcements', component: AnnouncementsComponent, canActivate: [authenticationGuard] },
  { path: 'courses/:id/modules', component: ModulesComponent, canActivate: [authenticationGuard] },
  { path: 'courses/:id/assignments', component: AssignmentsComponent, canActivate: [authenticationGuard] },
  { path: 'courses/:id/assignments/:assignmentId', component: AssignmentComponent, canActivate: [authenticationGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'settings', component: SettingsComponent, canActivate: [authenticationGuard] },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
