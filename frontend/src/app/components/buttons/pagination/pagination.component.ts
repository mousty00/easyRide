import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
    @Input() selectedPage : number = 0;
    @Input() totalPages : number[] = [];
    @Input() pageIndex!: number;
    @Input() getAll! : (pageNumber?: number) => void;
}
