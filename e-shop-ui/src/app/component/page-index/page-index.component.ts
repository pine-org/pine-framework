import {Component, Input, OnInit} from '@angular/core';
import {Page} from "../../service/Page";
import {Observable, Subscription} from "rxjs";

@Component({
    selector: 'app-page-index',
    templateUrl: './page-index.component.html',
    styleUrls: ['./page-index.component.css']
})
export class PageIndexComponent implements OnInit {

    @Input() page: Page = new Page();

    @Input() deletedItems: number[] = [];

    @Input() buttonShape: string = '';

    @Input() paginationButtonShape: string = 'rounded-0';

    @Input() paginationButtonBorder: string = 'border';

    @Input() hiddenText: boolean = false;

    @Input() hiddenIcon: boolean = false;

    @Input() indicesOffset = 0;

    @Input() indicesLimit = 5;

    @Input() pageIndices: boolean = true;
    @Input() deleteEvent: Observable<void>;
    @Input() deleteAllEvent: Observable<void>;
    private deleteEventSubscription: Subscription;
    private deleteAllEventSubscription: Subscription;

    constructor() {
    }

    ngOnInit(): void {
        if (this.deleteEvent != undefined)
            this.deleteEventSubscription = this.deleteEvent.subscribe(value => this.deleteEventHandler())
        if (this.deleteAllEvent != undefined)
            this.deleteAllEventSubscription = this.deleteAllEvent.subscribe(value => this.deleteAllEventHandler())
    }

    @Input() getPage = (index: number) => {

    }

    @Input() beforeNextPage = () => {
    }

    isActive(num: number) {
        return this.page.index == num ? 'btn-primary' : '';
    }

    currentPage = () => {
        this.beforeNextPage();
        this.getPage(this.page.index);
    }

    nextPage = () => {
        this.beforeNextPage();
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

    deleteEventHandler() {
        if (this.page.content == undefined || this.page.content.length == 1)
            this.previousPage();
        else
            this.currentPage();
    }

    deleteAllEventHandler() {
        if (this.page.content.length == 1 || this.deletedItems.length == this.page.content.length)
            this.previousPage();
        else
            this.currentPage();
    }

}
