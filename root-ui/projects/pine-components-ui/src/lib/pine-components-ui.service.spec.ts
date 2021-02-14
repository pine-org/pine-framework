import {TestBed} from '@angular/core/testing';

import {PineComponentsUiService} from './pine-components-ui.service';

describe('PineComponentsUiService', () => {
  let service: PineComponentsUiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PineComponentsUiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
