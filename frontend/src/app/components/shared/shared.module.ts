import { DropDownMenuComponent } from './drop-down-menu/drop-down-menu.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DropDownOptionComponent } from './drop-down-menu/drop-down-option/drop-down-option.component';
import { CustomButtonComponent } from './custom-button/custom-button.component';

@NgModule({
  declarations: [
    DropDownMenuComponent,
    DropDownOptionComponent,
    CustomButtonComponent,
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
  ],
  exports: [
    DropDownMenuComponent,
    CustomButtonComponent,
  ]
})
export class SharedModule { }
