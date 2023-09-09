import { DropDownMenuComponent } from './drop-down-menu/drop-down-menu.component';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DropDownOptionComponent } from './drop-down-menu/drop-down-option/drop-down-option.component';
import { CustomButtonComponent } from './custom-button/custom-button.component';
import { AssignmentUnitComponent } from './assignment-unit/assignment-unit.component';

@NgModule({
  declarations: [
    DropDownMenuComponent,
    DropDownOptionComponent,
    CustomButtonComponent,
    AssignmentUnitComponent,
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
  ],
  exports: [
    DropDownMenuComponent,
    CustomButtonComponent,
    AssignmentUnitComponent,
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
    NO_ERRORS_SCHEMA,
  ]
})
export class SharedModule { }
