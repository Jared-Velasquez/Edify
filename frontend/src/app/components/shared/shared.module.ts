import { DropDownMenuComponent } from './drop-down-menu/drop-down-menu.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    DropDownMenuComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    DropDownMenuComponent,
  ]
})
export class SharedModule { }
