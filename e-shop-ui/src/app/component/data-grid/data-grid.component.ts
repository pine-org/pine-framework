import {Service} from "../../service/AbstractService";
import {Component, Input, OnInit} from "@angular/core";
import {Icon, Properties, Text} from "../Properties";
import {Page} from "../../service/Page";

@Component({
  selector: 'app-data-grid',
  templateUrl: './data-grid.component.html',
  styleUrls: ['./data-grid.component.css']
})
export class DataGridComponent implements OnInit {

  @Input() title: string = "";

  @Input() columns: Properties[] = [];

  @Input() service: Service<any>;

  @Input() visibleIndicesLimit = 5;

  @Input() addButtonVisible: boolean = true;

  @Input() refreshButtonVisible: boolean = true;

  @Input() readButtonVisible: boolean = true;

  @Input() editButtonVisible: boolean = true;

  @Input() deleteButtonVisible: boolean = true;

  @Input() pageIndices: boolean = true;

  @Input() bunches: string[] = ['5', '10', '15', '20'];

  defaultBunch: string = this.bunches[0];

  checkedAllTuples: string = null;

  @Input() selectAllCheckbox: boolean = false;

  visibleIndicesOffset = 0;

  private _deleteAllButtonVisible: boolean = false;

  get deleteAllButtonVisible(): boolean {
    return this._deleteAllButtonVisible;
  }

  @Input()
  set deleteAllButtonVisible(value: boolean) {
    this._deleteAllButtonVisible = value;
    this.deleteButtonVisible = !value;
  }

  private _bunchDropdownVisible: boolean = true;

  get bunchDropdownVisible(): boolean {
    return this._bunchDropdownVisible;
  }

  bunchDropdown: Properties = Properties.builder(Text.builder("Bunch").build())
    .hidden(!this._bunchDropdownVisible)
    .build();
  columnSelectionDropdown: Properties = Properties.builder(Text.builder('Columns').build())
    .hidden(!this._columnSelectionDropdownVisible)
    .icon(Icon.iconOf('fa fa-angle-double-down'))
    .build();
  firstColumn: Properties = Properties.builder(Text.builder('#').build())
    .hidden(!this._firstColumnVisibility)
    .build();
  operationColumn: Properties = Properties.builder(Text.builder('operation').build())
    .hidden(!this._operationColumnVisible)
    .build();

  private _columnSelectionDropdownVisible: boolean = true;

  get columnSelectionDropdownVisible(): boolean {
    return this._columnSelectionDropdownVisible;
  }

  private hiddenText: boolean = false;

  page: Page = new Page();
  private hiddenIcon: boolean = false;

  private _firstColumnVisibility: boolean = true;

  get firstColumnVisibility(): boolean {
    return this._firstColumnVisibility;
  }

  @Input()
  set bunchDropdownVisible(value: boolean) {
    this._bunchDropdownVisible = value;
    this.bunchDropdown = Properties.builder(Text.builder("Bunch").build())
      .hidden(!this._bunchDropdownVisible)
      .build();
  }

  @Input()
  set columnSelectionDropdownVisible(value: boolean) {
    this._columnSelectionDropdownVisible = value;
    this.columnSelectionDropdown = Properties.builder(Text.builder('Columns').build())
      .hidden(!this._columnSelectionDropdownVisible)
      .icon(Icon.iconOf('fa fa-angle-double-down'))
      .build();
  }

  private _operationColumnVisible: boolean = true;

  get operationColumnVisible(): boolean {
    return this._operationColumnVisible;
  }

  @Input()
  set firstColumnVisibility(value: boolean) {
    this._firstColumnVisibility = value;
    this.firstColumn = Properties.builder(Text.builder('#').build())
      .hidden(!this._firstColumnVisibility)
      .build();
  }

  @Input()
  set operationColumnVisible(value: boolean) {
    this._operationColumnVisible = value;
    this.operationColumn = Properties.builder(Text.builder('operation').build())
      .hidden(!this._operationColumnVisible)
      .build();
  }

  deletedItems: number[] = [];

  ngOnInit() {
    this.getPage(this.page.index);
  }

  getData(page: Page) {
    this.service.list(page).subscribe((response: Page) => {
      this.page = response;
    })
  }

  delete = (id) => {
    this.service.delete(id).subscribe(() => {
      if (this.page.content.length == 1)
        this.previousPage();
      else
        this.currentPage();
    });
  }

  deleteAll = (...identities: any[]) => {
    this.service.deleteAll(identities).subscribe(() => {
      if (this.page.content.length == 1 || this.deletedItems.length == this.page.content.length)
        this.previousPage();
      else
        this.currentPage();
    });
  }

  deletedItemsChange(event) {
    if (event.target.checked) {
      this.deletedItems.push(event.target.value);
    } else {
      const index = this.deletedItems.findIndex(value => value == event.target.value);
      this.deletedItems.splice(index, 1);
    }
  }

  toggleCheckedAllTuples() {
    this.checkedAllTuples = this.checkedAllTuples == null ? "checked" : null;
  }

  bunchChange(value: any) {
    if (!this.bunches.includes('Default', 0)) {
      this.bunches.unshift('Default')
    }

    if (value == 'Default') {
      this.bunches.splice(0, 1);
      this.defaultBunch = this.bunches[0];
      this.bunchDropdown.title.content = 'Bunch';
    } else {
      this.defaultBunch = value;
      this.bunchDropdown.title.content = value;
    }

    this.getPage(0);
  }

  columnSelectionChange(column: Properties, event) {
    this.columns
      .filter(value => value.title.content == column.title.content)
      .forEach(value => value.hidden = !event.target.checked);
  }

  isActive(num: number) {
    return this.page.index == num ? {active: true} : {};
  }

  getPage = (index: number) => {
    this.page.index = index;
    this.page.size = parseInt(this.defaultBunch);
    this.getData(this.page);
  }

  currentPage = () => {
    this.getData(this.page);
  }

  nextPage = () => {
    if (this.page.index < this.page.indices.length - 1) {
      if (this.page.index == this.visibleIndicesOffset + this.visibleIndicesLimit - 1) {
        this.visibleIndicesOffset += this.visibleIndicesLimit
      }
      this.getPage(this.page.index + 1);
    }
  }

  previousPage = () => {
    if (this.page.index > 0) {
      if (this.page.index <= this.visibleIndicesOffset) {
        this.visibleIndicesOffset -= this.visibleIndicesLimit
      }
      this.getPage(this.page.index - 1);
    }
  }

  nextIndex() {
    if (this.visibleIndicesOffset < this.page.indices.length) {
      this.visibleIndicesOffset += this.visibleIndicesLimit
      this.getPage(this.visibleIndicesOffset);
    }
  }

  previousIndex() {
    if (this.visibleIndicesOffset > 0) {
      this.visibleIndicesOffset -= this.visibleIndicesLimit
      this.getPage(this.visibleIndicesOffset + this.visibleIndicesLimit - 1);
    }
  }

}
