import {TestBed} from '@angular/core/testing';

import {EshopComponentsUiService} from './eshop-components-ui.service';

describe('EshopComponentsUiService', () => {
  let service: EshopComponentsUiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EshopComponentsUiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
