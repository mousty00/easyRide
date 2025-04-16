import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges,} from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css'],
})
export class SearchBarComponent<T> implements OnChanges {
  @Input() placeholder: string = '';
  @Input() list: T[] = [];
  @Input() searchTopic: string = '';
  @Output() resultsChanged = new EventEmitter<T[]>();

  searchTerm: string = '';
  filteredResults: T[] = [];

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['list'] || changes['searchTopic']) {
      this.updateResults();
    }
  }

  updateResults(): void {
    this.filteredResults = this.search();
    this.resultsChanged.emit(this.filteredResults);
  }

  onSearchTermChange(): void {
    this.updateResults();
  }

  search(): T[] {
    if (!this.list) {
      return [];
    }
    if (!this.searchTerm || this.searchTerm.trim() === '') {
      return this.list;
    }
    const term = this.searchTerm.toLowerCase().trim();

    return this.list.filter((item: any) => {
      if (!item || !this.searchTopic) {
        return false;
      }
      const value = item[this.searchTopic];
      if (typeof value === 'string') {
        return value.toLowerCase().includes(term);
      } else if (value !== null && value !== undefined) {
        return String(value).toLowerCase().includes(term);
      }
      return false;
    });
  }
}
