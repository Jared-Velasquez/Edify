import { Component, Input } from '@angular/core';
import { hoverAnimation, hoverChildAnimation } from 'src/app/animations/shared_animations';

@Component({
  selector: 'app-drop-down-option',
  templateUrl: './drop-down-option.component.html',
  styleUrls: ['./drop-down-option.component.css'],
  animations: [
    hoverAnimation,
    hoverChildAnimation,
  ]
})
export class DropDownOptionComponent {
  @Input() option: string;
  isOver: boolean;

  constructor() {
    this.option = "";
    this.isOver = false;
  }
}
