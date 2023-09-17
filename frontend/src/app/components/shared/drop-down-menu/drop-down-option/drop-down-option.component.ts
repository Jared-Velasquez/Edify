import { Component, Input } from '@angular/core';
import { hoverAnimation, hoverChildAnimation } from 'src/app/animations/shared_animations';

@Component({
  selector: 'app-drop-down-option',
  templateUrl: './drop-down-option.component.html',
  styleUrls: ['./drop-down-option.component.scss'],
  animations: [
    hoverAnimation,
    hoverChildAnimation,
  ]
})
export class DropDownOptionComponent {
  @Input() option: string;
  @Input() chosenOption: boolean;
  isOver: boolean;

  constructor() {
    this.option = "";
    this.isOver = false;
    this.chosenOption = false;
  }
}
