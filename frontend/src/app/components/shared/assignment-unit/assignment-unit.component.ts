import { Component, Input } from '@angular/core';
import { Assignment, AssignmentEmpty } from 'src/app/models';

@Component({
  selector: 'app-assignment-unit',
  templateUrl: './assignment-unit.component.html',
  styleUrls: ['./assignment-unit.component.css']
})
export class AssignmentUnitComponent {
  @Input() assignment: Assignment;
  @Input() score: number | null;

  constructor() {
    this.assignment = AssignmentEmpty;
    this.score = null;
  }

  convertDateToString(date: Date): string {
    return date.toLocaleString();
  }
}
