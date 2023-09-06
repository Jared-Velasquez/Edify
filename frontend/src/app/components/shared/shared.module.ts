import { DropDownMenuComponent } from './drop-down-menu/drop-down-menu.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DropDownOptionComponent } from './drop-down-menu/drop-down-option/drop-down-option.component';

@NgModule({
  declarations: [
    DropDownMenuComponent,
    DropDownOptionComponent,
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
  ],
  exports: [
    DropDownMenuComponent,
  ]
})
export class SharedModule { }
