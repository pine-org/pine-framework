import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DeleteBunchButtonComponent} from './delete-bunch-button.component';

describe('DeleteBunchButtonComponent', () => {
    let component: DeleteBunchButtonComponent;
    let fixture: ComponentFixture<DeleteBunchButtonComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DeleteBunchButtonComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(DeleteBunchButtonComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
