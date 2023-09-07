import { Component, Input } from '@angular/core';
import { hoverAnimation, hoverChildAnimation } from 'src/app/animations/shared_animations';

@Component({
  selector: 'app-custom-button',
  templateUrl: './custom-button.component.html',
  styleUrls: ['./custom-button.component.css'],
  animations: [
    hoverAnimation,
    hoverChildAnimation,
  ]
})
export class CustomButtonComponent {
  @Input() title: string;
  isOver: boolean;

  constructor() {
    this.title = "";
    this.isOver = false;
  }
}
