import {Service} from "../../service/AbstractService";
import {Component, Input, OnInit, Output} from "@angular/core";
import {Icon, Properties, Text} from "../Properties";
import {Page} from "../../service/Page";
import {Subject} from "rxjs";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-data-grid',
  templateUrl: './data-grid.component.html',
  styleUrls: ['./data-grid.component.css']
})
export class DataGridComponent implements OnInit {

  @Input() page: Page = new Page();

  deletedItems: number[] = [];

  checkedAllTuples: string = null;

  @Input() title: string = "";

  @Input() service: Service<any>;

  @Input() bunches: string[] = ['5', '10', '15', '20'];

  defaultBunch: string = this.bunches[0];

  @Input() contentPadding: string = '2';

  @Input() checkBoxPadding: string = '3';

  @Input() rowHeight: string = '1';

  @Input() headerHeight: string = '3';

  @Input() tableWidth: string = '100';

  @Input() buttonShape: string = '';

  @Input() buttonSlideTextHidden: boolean = true;

  @Input() buttonSlideIconHidden: boolean = false;

  @Input() paginationButtonShape: string = 'rounded-0';

  @Input() paginationButtonBorder: string = 'border';

  @Input() hiddenText: boolean = false;

  @Input() hiddenIcon: boolean = false;

  @Input() selectAllCheckboxVisible: boolean = false;

  @Input() indicesOffset = 0;

  @Input() indicesLimit = 5;

  @Input() sliderIndicesLimit = 3;

  @Input() addButtonVisible: boolean = true;

  @Input() refreshMenuButtonVisible: boolean = true;

  @Input() refreshButtonVisible: boolean = false;

  @Input() readButtonVisible: boolean = true;

  @Input() editButtonVisible: boolean = true;

  @Input() deleteButtonVisible: boolean = true;

  @Input() pageIndices: boolean = true;

  @Input() photoPreview: boolean = false;

  @Input() slide: boolean = true;

  @Output() deleteEvent: Subject<void> = new Subject<void>();

  @Output() deleteAllEvent: Subject<void> = new Subject<void>();

// ######################################################################################
  @Input() private makingCoffee: boolean = false;

  constructor(private modalService: NgbModal) {
  }

// ######################################################################################

// ######################################################################################
  private _columns: Properties[] = [];

  get columns(): Properties[] {
    return this._columns;
  }

  @Input()
  set columns(value: Properties[]) {
    this._columns = value;
    this.page.projections = this._columns.map((v, i) => v.field.content);
  }

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
  private _readOnly: boolean = false;

  get readOnly(): boolean {
    return this._readOnly;
  }

  @Input()
  set readOnly(value: boolean) {
    this._readOnly = value;
    this.refreshButtonVisible = !value;
    this.addButtonVisible = !value;
    this.deleteAllButtonVisible = !value;
    this.readButtonVisible = !value;
    this.editButtonVisible = !value;
    this.deleteButtonVisible = !value;
    this.refreshMenuButtonVisible = !value;
    this.columnSelectionDropdownVisible = !value;
    this.bunchDropdownVisible = !value;
    this.selectAllCheckboxVisible = !value;
  }

  // ######################################################################################

  // ######################################################################################
  private _bunchDropdownVisible: boolean = true;

  get bunchDropdownVisible(): boolean {
    return this._bunchDropdownVisible;
  }

  @Input()
  set bunchDropdownVisible(value: boolean) {
    this._bunchDropdownVisible = value;
    this.bunchDropdown = Properties.builder(Text.builder("Bunch").build())
      .hidden(!this._bunchDropdownVisible)
      .build();
  }

  bunchDropdown: Properties = Properties.builder(Text.builder("Bunch").build())
    .hidden(!this._bunchDropdownVisible)
    .build();

  // ######################################################################################

  // ######################################################################################

  private _operationColumnVisible: boolean = true;
  operationColumn: Properties = Properties.builder(Text.builder('Operation').build())
    .hidden(!this._operationColumnVisible)
    .build();

  get operationColumnVisible(): boolean {
    return this._operationColumnVisible;
  }

  @Input()
  set operationColumnVisible(value: boolean) {
    this._operationColumnVisible = value;
    this.operationColumn = Properties.builder(Text.builder('Operation').build())
      .hidden(!this._operationColumnVisible)
      .build();
  }

  // ######################################################################################

  // ######################################################################################
  private _firstColumnVisibility: boolean = true;

  firstColumn: Properties = Properties.builder(Text.builder('#').build())
    .hidden(!this._firstColumnVisibility)
    .build();

  get firstColumnVisibility(): boolean {
    return this._firstColumnVisibility;
  }

  @Input()
  set firstColumnVisibility(value: boolean) {
    this._firstColumnVisibility = value;
    this.firstColumn = Properties.builder(Text.builder('#').build())
      .hidden(!this._firstColumnVisibility)
      .build();
  }

  // ######################################################################################

  // ######################################################################################
  private _columnSelectionDropdownVisible: boolean = true;

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

  // ######################################################################################

  // ######################################################################################

  openMakingCoffeeModal = (content) => {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then(
      (result) => {
      },
      (reason) => {
      });
  }

  ngOnInit() {
    this.getPage(this.page.index);
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
    if (this.checkedAllTuples == null) {
      this.checkedAllTuples = "checked";
      this.deletedItems = this.page.content.map(value => value.id);
    } else {
      this.checkedAllTuples = null;
      this.deletedItems.splice(0, this.page.content.length);
    }
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
    this._columns
      .filter(value => value.title.content == column.title.content)
      .forEach(value => value.hidden = !event.target.checked);
  }

  delete = (id) => {
    this.service.delete(id).subscribe(() => {
      this.deleteEvent.next();
    });
  }

  deleteAll = (identities: any[]) => {
    this.service.deleteAll(identities).subscribe(() => {
      this.deleteAllEvent.next();
    });
  }

  refreshCurrentPage = () => {
    this.resetCheckbox()
    this.getPage(this.page.index);
  }

  getPage = (index: number) => {
    this.resetCheckbox();
    this.page.index = index;
    this.page.size = parseInt(this.defaultBunch);
    this.service.list(this.page).subscribe((response: Page) => {
      this.page = response;
    })
  }

  resetCheckbox = () => {
    this.checkedAllTuples = null;
  }
}
