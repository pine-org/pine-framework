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

  page: Page = new Page();

  deletedItems: number[] = [];

  checkedAllTuples: string = null;

  @Input() title: string = "";

  @Input() columns: Properties[] = [];

  @Input() service: Service<any>;

  @Input() bunches: string[] = ['5', '10', '15', '20'];

  defaultBunch: string = this.bunches[0];

  @Input() hiddenText: boolean = false;

  @Input() hiddenIcon: boolean = false;

  @Input() selectAllCheckboxVisible: boolean = false;

  @Input() indicesOffset = 0;

  @Input() indicesLimit = 5;

  @Input() addButtonVisible: boolean = true;

  @Input() refreshButtonVisible: boolean = true;

  @Input() readButtonVisible: boolean = true;

  @Input() editButtonVisible: boolean = true;

  @Input() deleteButtonVisible: boolean = true;

  @Input() pageIndices: boolean = true;

  // ######################################################################################
  private _deleteAllButtonVisible: boolean = false;

  get deleteAllButtonVisible(): boolean {
    return this._deleteAllButtonVisible;
  }

  @Input()
  set deleteAllButtonVisible(value: boolean) {
    this._deleteAllButtonVisible = value;
    this.deleteButtonVisible = !value;
  }

  // ######################################################################################

  // ######################################################################################
  private _bunchDropdownVisible: boolean = true;

  get bunchDropdownVisible(): boolean {
    return this._bunchDropdownVisible;
  }

  // ######################################################################################
  private _operationColumnVisible: boolean = true;

  bunchDropdown: Properties = Properties.builder(Text.builder("Bunch").build())
    .hidden(!this._bunchDropdownVisible)
    .build();
  // ######################################################################################
  operationColumn: Properties = Properties.builder(Text.builder('Operation').build())
    .hidden(!this._operationColumnVisible)
    .build();
  // ######################################################################################
  private _firstColumnVisibility: boolean = true;

  @Input()
  set bunchDropdownVisible(value: boolean) {
    this._bunchDropdownVisible = value;
    this.bunchDropdown = Properties.builder(Text.builder("Bunch").build())
      .hidden(!this._bunchDropdownVisible)
      .build();
  }

  // ######################################################################################
  private _columnSelectionDropdownVisible: boolean = true;

  // ######################################################################################

  get columnSelectionDropdownVisible(): boolean {
    return this._columnSelectionDropdownVisible;
  }

  @Input()
  set columnSelectionDropdownVisible(value: boolean) {
    this._columnSelectionDropdownVisible = value;
    this.columnSelectionDropdown = Properties.builder(Text.builder('Columns').build())
      .hidden(!this._columnSelectionDropdownVisible)
      .icon(Icon.builder('fa fa-angle-double-down').build())
      .build();
  }

  columnSelectionDropdown: Properties = Properties.builder(Text.builder('Columns').build())
    .hidden(!this._columnSelectionDropdownVisible)
    .icon(Icon.builder('fa fa-angle-double-down').build())
    .build();

  firstColumn: Properties = Properties.builder(Text.builder('#').build())
    .hidden(!this._firstColumnVisibility)
    .build();

  // ######################################################################################

  get firstColumnVisibility(): boolean {
    return this._firstColumnVisibility;
  }

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
    this.operationColumn = Properties.builder(Text.builder('Operation').build())
      .hidden(!this._operationColumnVisible)
      .build();
  }

  // ######################################################################################

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
    return this.page.index == num ? 'btn-primary' : '';
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
      if (this.page.index == this.indicesOffset + this.indicesLimit - 1) {
        this.indicesOffset += this.indicesLimit
      }
      this.getPage(this.page.index + 1);
    }
  }

  previousPage = () => {
    if (this.page.index > 0) {
      if (this.page.index <= this.indicesOffset) {
        this.indicesOffset -= this.indicesLimit
      }
      this.getPage(this.page.index - 1);
    }
  }

  nextIndex() {
    if (this.indicesOffset < this.page.indices.length) {
      this.indicesOffset += this.indicesLimit
      this.getPage(this.indicesOffset);
    }
  }

  previousIndex() {
    if (this.indicesOffset > 0) {
      this.indicesOffset -= this.indicesLimit
      this.getPage(this.indicesOffset + this.indicesLimit - 1);
    }
  }

}
