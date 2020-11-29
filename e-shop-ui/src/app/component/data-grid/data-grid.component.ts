import {Service} from "../../service/AbstractService";
import {Component, Input, OnInit} from "@angular/core";
import {Icon, Properties, Text} from "../Properties";
import {Page} from "../../service/Page";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

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

    @Input() contentPadding: string = '3';

    @Input() checkBoxPadding: string = '3';

    @Input() rowHeight: string = '3';

    @Input() headerHeight: string = '3';

    @Input() tableWidth: string = '100';

    @Input() buttonShape: string = '';

    @Input() paginationButtonShape: string = 'rounded-0';

    @Input() paginationButtonBorder: string = 'border';

    @Input() hiddenText: boolean = false;

    @Input() hiddenIcon: boolean = false;

    @Input() selectAllCheckboxVisible: boolean = false;

    @Input() indicesOffset = 0;

    @Input() indicesLimit = 5;

    @Input() addButtonVisible: boolean = true;

    @Input() refreshMenuButtonVisible: boolean = true;

    @Input() refreshButtonVisible: boolean = false;

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
    private _operationColumnVisible: boolean = true;

    // ######################################################################################
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

    albumIndex: number = 0

    @Input() photo: string = "";

    @Input() photoPreview: boolean = false;

    @Input() slide: boolean = false;

    photoColumn: Properties = Properties.builder(Text.builder('Photo').build())
        .hidden(this.photo == '')
        .build();

    constructor(private modalService: NgbModal) {
    }

    getAlbum(item) {
        let album = [];
        album.push(item);
        album.push(item);
        return album.map(value => "data:image/jpeg;base64," + value);
    }

    // ######################################################################################

    getPhotoByIndex(item, index) {
        let album = [];
        album.push(item[this.photo]);
        album.push(item[this.photo]);
        return "data:image/jpeg;base64," + album[index];
    }

    // ######################################################################################

    ngOnInit() {
        this.getPage(this.page.index);
    }

    getData(page: Page) {
        page.projections.push(this.photo)
        this.service.list(page).subscribe((response: Page) => {
            this.page = response;
        })
    }


    openAlbumModal = (content) => {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then(
            (result) => {
            },
            (reason) => {
            });
    }

    previousPhoto = () => {
        this.albumIndex--;
    }

    nextPhoto = () => {
        this.albumIndex++;
    }


    delete = (id) => {
        this.service.delete(id).subscribe(() => {
            if (this.page.content.length == 1)
                this.previousPage();
            else
                this.currentPage();
        });
    }

    deleteAll = (identities: any[]) => {
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
        this.columns
            .filter(value => value.title.content == column.title.content)
            .forEach(value => value.hidden = !event.target.checked);
    }

    isActive(num: number) {
        return this.page.index == num ? 'btn-primary' : '';
    }

    getPage = (index: number) => {
        this.checkedAllTuples = null;
        this.page.index = index;
        this.page.size = parseInt(this.defaultBunch);
        this.getData(this.page);
    }

    currentPage = () => {
        this.checkedAllTuples = null;
        this.getData(this.page);
    }

    nextPage = () => {
        this.checkedAllTuples = null;
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

    nextIndex = () => {
        if (this.indicesOffset < this.page.indices.length) {
            this.indicesOffset += this.indicesLimit
            this.getPage(this.indicesOffset);
        }
    }

    previousIndex = () => {
        if (this.indicesOffset > 0) {
            this.indicesOffset -= this.indicesLimit
            this.getPage(this.indicesOffset + this.indicesLimit - 1);
        }
    }

}
