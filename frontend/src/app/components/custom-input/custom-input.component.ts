import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-custom-input',
  templateUrl: './custom-input.component.html',
})
export class CustomInputComponent {
  @Input() value?: string = '';
  @Input() label?: string = '';
  @Input() id?: string | number = '';
  @Input() type: string = '';
  @Input() disabled: boolean = false;
  @Input() hasError: boolean = false;
  @Input() error: string = '';

  @Output() valueChange = new EventEmitter<string>();

  onInputChange(event: any) {
    this.value = event.target.value;
    this.valueChange.emit(this.value);
  }
}
