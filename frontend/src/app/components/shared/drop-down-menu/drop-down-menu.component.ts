import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { hoverAnimation, hoverChildAnimation } from 'src/app/animations/shared_animations';
import { DropDownMenuEmpty, DropDownMenuInterface } from 'src/app/models';

const dropDownMenuStyle = {
  borderRadius: '0.375rem',
  borderWidth: '2px',
  backgroundColor: '#0D1821',
  borderColor: '#0D1821',
  color: 'rgba(255, 255, 255, 0.7)',
}

@Component({
  selector: 'app-drop-down-menu',
  templateUrl: './drop-down-menu.component.html',
  styleUrls: ['./drop-down-menu.component.css'],
  animations: [
    trigger('revealOptions', [
      state('show', style({
        height: '*',
        overflow: 'hidden',
        ...dropDownMenuStyle,
      })),
      state('hide', style({
        height: '0',
        overflow: 'hidden',
      })),
      transition('show => hide', [
        style({
          overflow: 'hidden',
          ...dropDownMenuStyle,
        }), 
        animate('250ms ease-in-out', style({
          height: '0',
          ...dropDownMenuStyle,
        })),
      ]),
      transition('hide => show', [ 
        style({
          overflow: 'hidden',
          ...dropDownMenuStyle,
        }),
        animate('250ms ease-in-out', style({
          height: '*',
          ...dropDownMenuStyle,
        })),
      ]),
      transition('void <=> *', animate(0)),
    ])
  ],
})
export class DropDownMenuComponent {
  @Input() menu: DropDownMenuInterface;
  @Output() optionChosen = new EventEmitter<string>();
  chosenOption: string;
  dropDownExpanded: boolean;
  isOver: boolean;

  constructor() {
    this.menu = DropDownMenuEmpty;
    this.dropDownExpanded = false;
    this.isOver = false;
    this.chosenOption = "";
  }

  toggleExpanded() {
    this.dropDownExpanded = !this.dropDownExpanded;
  }

  onChooseOption(option: string) {
    this.chosenOption = option;
    this.optionChosen.emit(option);
  }
}
