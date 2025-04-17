import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-toggle-button',
  templateUrl: './toggle-button.component.html',
  styleUrls: ['./toggle-button.component.css']
})
export class ToggleButtonComponent {
    @Output() changed = new EventEmitter<boolean>();
    @Input() isChecked = false;

  toggleChanged(event: any) {
    this.isChecked = event.target.checked;
    this.changed.emit(this.isChecked);
  }
}
