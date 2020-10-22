import {Service} from "../../service/AbstractService";
import {Component, Input, OnInit} from "@angular/core";
import {Icon, Properties} from "../Properties";
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

  page: Page = new Page();
  deleted: number[] = [];

  dataBunches: string[] = ['5', '10', '15', '20'];
  defaultDataBunch: string = this.dataBunches[0];
  dropdownBunch: Properties = Properties.builder('Bunch').build();

  dropdownColumnSelection: Properties = Properties.builder('Columns').icon(Icon.iconOf('fa fa-angle-double-down')).build();

  columnOperation: Properties = Properties.builder('Operation').build();

  checkedAllRows: string = null;
  columnCheckbox: Properties = Properties.builder('#').hiddenTitle().build();

  minVisibleIndex = 0;
  @Input() lengthOfVisibleIndex = 5;

  ngOnInit() {
    this.getPage(this.page.index);
  }

  getData(page: Page) {
    this.service.list(page).subscribe((response: Page) => {
      this.page = response;
    })
  }

  delete = (id) => {
    this.service.delete(id)
  }

  deleteAll = (...identities: any[]) => {
    this.service.deleteAll(identities).subscribe(() => {
      if (this.page.content.length == 1 || this.deleted.length == this.page.content.length) {
        this.previousPage();
      } else {
        this.currentPage();
      }
    });
  }

  updateDeleted(event) {
    if (event.target.checked) {
      this.deleted.push(event.target.value);
    } else {
      const index = this.deleted.findIndex(value => value == event.target.value);
      this.deleted.splice(index, 1);
    }
  }

  toggleCheckedAll() {
    this.checkedAllRows = this.checkedAllRows == null ? "checked" : null;
  }

  changeBunchOfData(value: any) {
    if (!this.dataBunches.includes('Default', 0)) {
      this.dataBunches.unshift('Default')
    }

    if (value == 'Default') {
      this.dataBunches.splice(0, 1);
      this.defaultDataBunch = this.dataBunches[0];
      this.dropdownBunch.title.content = 'Bunch';
    } else {
      this.defaultDataBunch = value;
      this.dropdownBunch.title.content = value;
    }

    this.getPage(0);
  }

  selectColumn(column: Properties, event) {
    this.columns
      .filter(value => value.title.content == column.title.content)
      .forEach(value => value.hidden = !event.target.checked);
  }

  isActive(num: number) {
    return this.page.index == num ? {active: true} : {};
  }

  getPage = (index: number) => {
    this.page.index = index;
    this.page.size = parseInt(this.defaultDataBunch);
    this.getData(this.page);
  }

  currentPage = () => {
    this.getData(this.page);
  }

  nextPage = () => {
    if (this.page.index < this.page.indices.length - 1) {
      if (this.page.index == this.minVisibleIndex + this.lengthOfVisibleIndex - 1) {
        this.minVisibleIndex += this.lengthOfVisibleIndex
      }
      this.getPage(this.page.index + 1);
    }
  }

  previousPage = () => {
    if (this.page.index > 0) {
      if (this.page.index <= this.minVisibleIndex) {
        this.minVisibleIndex -= this.lengthOfVisibleIndex
      }
      this.getPage(this.page.index - 1);
    }
  }

  nextIndices() {
    if (this.minVisibleIndex < this.page.indices.length) {
      this.minVisibleIndex += this.lengthOfVisibleIndex
      this.getPage(this.minVisibleIndex);
    }
  }

  previousIndices() {
    if (this.minVisibleIndex > 0) {
      this.minVisibleIndex -= this.lengthOfVisibleIndex
      this.getPage(this.minVisibleIndex + this.lengthOfVisibleIndex - 1);
    }
  }
}
