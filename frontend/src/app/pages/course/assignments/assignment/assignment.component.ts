import { Component, Input } from '@angular/core';
import { Assignment, AssignmentEmpty } from 'src/app/models';

@Component({
  selector: 'app-assignment',
  templateUrl: './assignment.component.html',
  styleUrls: ['./assignment.component.css']
})
export class AssignmentComponent {
  @Input() assignment: Assignment;

  constructor() {
    this.assignment = AssignmentEmpty;
  }
}
