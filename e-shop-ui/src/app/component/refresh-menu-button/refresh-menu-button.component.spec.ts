import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RefreshMenuButtonComponent} from './refresh-menu-button.component';

describe('RefreshMenuButtonComponent', () => {
  let component: RefreshMenuButtonComponent;
  let fixture: ComponentFixture<RefreshMenuButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RefreshMenuButtonComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RefreshMenuButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
