import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './components/core/core.module';
import { PagesModule } from './pages/pages.module';
import { appReducer } from 'src/app/store/reducers/app.reducers';
import { AuthInterceptor } from 'src/app/auth/authentication/auth.interceptor';
import { CoursesEffects } from './store/effects/courses.effects';
import { DropDownMenuComponent } from './components/shared/drop-down-menu/drop-down-menu.component';

@NgModule({
  declarations: [
    AppComponent,
    DropDownMenuComponent,
  ],
  imports: [
    RouterModule,
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    StoreModule.forRoot(appReducer),
    EffectsModule.forRoot([CoursesEffects]),
    BrowserAnimationsModule,
    HttpClientModule,
    FontAwesomeModule,
    CoreModule,
    PagesModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
