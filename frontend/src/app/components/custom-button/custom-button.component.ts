import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-custom-button',
  templateUrl: './custom-button.component.html'
})
export class CustomButtonComponent<T> {
  @Input() background: string = '';
  @Input() text: string = '';
  @Input() icon: string = '';
  @Input() object: any;
  @Input() click: ((obj?: any) => void) | null = null;
  @Input() disabled: boolean = false;

  handleClick(): void {
    if (this.click && this.object) {
      this.click(this.object);
    } else if (this.click) {
      this.click();
    }
  }
}
