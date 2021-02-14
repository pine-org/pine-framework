import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PineComponentsUiComponent} from './pine-components-ui.component';

describe('PineComponentsUiComponent', () => {
  let component: PineComponentsUiComponent;
  let fixture: ComponentFixture<PineComponentsUiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PineComponentsUiComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PineComponentsUiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
