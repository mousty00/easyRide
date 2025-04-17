import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html'
})
export class CardComponent<T> {
    @Input() id: number = 0;
    @Input() object!: T;
    @Input() title: string = "";
    @Input() delete! : (object?: T) => void;
    @Input() edit! : (object?: T) => void;
    @Input() detailHref: string = ""
    @Input() editable: boolean = true;

    constructor(private router: Router){}

    navigateTo(href: string) {
        this.router.navigate([href])
    }
}
