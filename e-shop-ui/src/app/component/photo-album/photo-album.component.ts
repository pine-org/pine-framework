import {Component, Input, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Page} from "../../service/Page";

@Component({
    selector: 'app-photo-album',
    templateUrl: './photo-album.component.html',
    styleUrls: ['./photo-album.component.css']
})
export class PhotoAlbumComponent implements OnInit {

    @Input() page: Page = new Page();
    @Input() bunches: string[] = ['1', '5', '10', '15', '20'];
    defaultBunch: string = this.bunches[0];
    @Input() photoPreview: boolean = false;
    @Input() slide: boolean = false;
    @Input() hiddenText: boolean = false;
    @Input() hiddenIcon: boolean = false;
    @Input() buttonShape: string = '';
    @Input() deletedItems: number[] = [];
    @Input() paginationButtonShape: string = 'rounded-0';
    @Input() paginationButtonBorder: string = 'border';
    @Input() indicesOffset = 0;
    @Input() indicesLimit = 5;
    @Input() pageIndices: boolean = true;

    constructor(private modalService: NgbModal) {
    }

    private _photos = [];

    get photos(): any[] {
        return this._photos;
    }

    @Input()
    set photos(value: any[]) {
        if (Array.isArray(value))
            this._photos = value;
        else if (value != null)
            this.photos.push(value);
    }

    ngOnInit(): void {
        this.getPage(0);
    }

    openAlbumModal = (content) => {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then(
            (result) => {
            },
            (reason) => {
            });
    }

    getAllPhotos() {
        if (this._photos == undefined || this._photos == null)
            return "";

        return this._photos.map(value => "data:image/jpeg;base64," + value);
    }

    getPage = (index: number) => {
        this.page.index = index;
        this.page.size = parseInt(this.defaultBunch);
        this.page.content = this._photos.filter((v, i) => i == index);
        this.page.indices = this._photos.map((v, i) => i);
    }

    convertToPhotoFormat(item) {
        return "data:image/jpeg;base64," + item;
    }
}
