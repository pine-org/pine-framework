import {NgModule} from '@angular/core';
import {EshopComponentsUiComponent} from './eshop-components-ui.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";


@NgModule({
  declarations: [EshopComponentsUiComponent],
  imports: [
    FontAwesomeModule
  ],
  exports: [EshopComponentsUiComponent]
})
export class EshopComponentsUiModule {
}
