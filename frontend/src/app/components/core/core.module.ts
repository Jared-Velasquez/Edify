import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BodyComponent } from './body/body.component';
import { AppRoutingModule } from '../../app-routing.module';
import { NavLinkComponent } from './navbar/nav-link/nav-link.component';

@NgModule({
  declarations: [
    FooterComponent,
    NavbarComponent,
    BodyComponent,
    NavLinkComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    AppRoutingModule,
    BrowserAnimationsModule,
  ],
  exports: [
    FooterComponent,
    NavbarComponent,
    BodyComponent,
  ]
})
export class CoreModule { }
