import { animate, state, style, transition, trigger, query, sequence, stagger } from '@angular/animations';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Module } from 'src/app/models';
import { ModuleEmpty } from 'src/app/models/httpResponseModels';

@Component({
  selector: 'app-module',
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.scss'],
  animations: [
    trigger('rotate', [
      state('default', style({ transform: 'rotate(0)' })),
      state('rotated', style({ transform: 'rotate(-90deg)' })),
      transition('rotated <=> default', animate('250ms ease-in-out'))
    ]),
    // Drop-down menu animation by Nichola Alkhouri
    // https://stackblitz.com/edit/drop-down-menu-animation
    trigger("moduleAnimation", [
      transition(":enter", [
        style({ height: 0, overflow: "hidden" }),
        query(".assignment", [
          style({ opacity: 0, transform: "translateY(-50px)" })
        ]),
        sequence([
          animate("200ms", style({ height: "*" })),
          query(".assignment", [
            stagger(50, [
              animate("400ms ease", style({ opacity: 1, transform: "none" }))
            ])
          ])
        ])
      ]),
      transition(":leave", [
        style({ height: "*", overflow: "hidden" }),
        query(".assignment", [style({ opacity: 1, transform: "none" })]),
        sequence([
          query(".assignment", [
            stagger(50, [
              animate(
                "350ms ease",
                style({ opacity: 0, transform: "translateY(-50px)" })
              )
            ])
          ]),
          animate("200ms ease-in-out", style({ height: 0 }))
        ])
      ])
    ])
  ],
})
export class ModuleComponent {
  @Input() module: Module;
  @Input() courseId: number;
  moduleExpanded: boolean;

  constructor(private router: Router) {
    this.module = ModuleEmpty;
    this.courseId = 0;
    this.moduleExpanded = false;
  }

  onClick() {
    if (this.module.assignments.length > 0)
      this.moduleExpanded = !this.moduleExpanded;
  }

  onAssignmentClick(assignmentId: number) {
    this.router.navigateByUrl(`/courses/${this.courseId}/assignments/${assignmentId}`)
  }

  convertDateToString(date: Date): string {
    return date.toLocaleString();
  }
}
