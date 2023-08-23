import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BodyComponent } from './body/body.component';
import { AppRoutingModule } from '../../app-routing.module';

@NgModule({
  declarations: [
    FooterComponent,
    NavbarComponent,
    BodyComponent,
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    FooterComponent,
    NavbarComponent,
    BodyComponent,
  ]
})
export class CoreModule { }
