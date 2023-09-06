import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DropDownMenuEmpty, DropDownMenuInterface } from 'src/app/models';

@Component({
  selector: 'app-drop-down-menu',
  templateUrl: './drop-down-menu.component.html',
  styleUrls: ['./drop-down-menu.component.css']
})
export class DropDownMenuComponent {
  @Input() menu: DropDownMenuInterface;
  @Output() optionChosen = new EventEmitter<string>();
  dropDownExpanded: boolean;

  constructor() {
    this.menu = DropDownMenuEmpty;
    this.dropDownExpanded = false;
  }

  toggleExpanded() {
    this.dropDownExpanded = !this.dropDownExpanded;
  }

  onChooseOption(option: string) {
    this.optionChosen.emit(option);
  }
}
