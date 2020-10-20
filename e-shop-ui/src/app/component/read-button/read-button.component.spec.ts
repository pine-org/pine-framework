import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ReadButtonComponent} from './read-button.component';

describe('ReadButtonComponent', () => {
  let component: ReadButtonComponent;
  let fixture: ComponentFixture<ReadButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ReadButtonComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
