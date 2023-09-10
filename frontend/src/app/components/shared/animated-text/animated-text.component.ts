import { animate, AnimationEvent, query, stagger, style, transition, trigger } from '@angular/animations';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-animated-text',
  templateUrl: './animated-text.component.html',
  styleUrls: ['./animated-text.component.css'],
  animations: [
    trigger('textFade', [
      transition(':enter', [
        query('.character', [
          style({
            opacity: 0,
          }),
          stagger(60, [
            animate('100ms cubic-bezier(0.35, 0, 0.25, 1)',
            style({
              opacity: 1,
            }))
          ])
        ])
      ])
    ])
  ]
})
export class AnimatedTextComponent implements OnInit {
  @Input() text: string;
  @Input() enabled: boolean;
  @Output() finished = new EventEmitter<boolean>();
  characters: string[];

  constructor() {
    this.text = "";
    this.characters = [];
    this.enabled = false;
  }

  ngOnInit() {
    this.characters = [...this.text];
  }

  animationFinished(event: AnimationEvent) {
    if (event)
      this.finished.emit(true);
  }
}
