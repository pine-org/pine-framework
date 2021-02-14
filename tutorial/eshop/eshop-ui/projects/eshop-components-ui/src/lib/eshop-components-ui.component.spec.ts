import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EshopComponentsUiComponent} from './eshop-components-ui.component';

describe('EshopComponentsUiComponent', () => {
  let component: EshopComponentsUiComponent;
  let fixture: ComponentFixture<EshopComponentsUiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EshopComponentsUiComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EshopComponentsUiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
