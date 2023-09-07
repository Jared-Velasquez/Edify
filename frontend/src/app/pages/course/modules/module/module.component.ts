import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, Input } from '@angular/core';
import { Module } from 'src/app/models';
import { ModuleEmpty } from 'src/app/models/httpResponseModels';

@Component({
  selector: 'app-module',
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.scss'],
  animations: [
    trigger('rotate', [
      state('default', style({ transform: 'rotate(0)' })),
      state('rotated', style({ transform: 'rotate(90deg)' })),
      transition('rotated <=> default', animate('400ms ease-in-out'))
    ])
  ],
})
export class ModuleComponent {
  @Input() module: Module;
  moduleExpanded: boolean;

  constructor() {
    this.module = ModuleEmpty;
    this.moduleExpanded = false;
  }

  onClick() {
    this.moduleExpanded = !this.moduleExpanded;
  }
}
