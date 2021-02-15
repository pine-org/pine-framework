import {NgModule} from '@angular/core';
import {PineComponentsUiComponent} from './pine-components-ui.component';
import {ButtonAddComponent} from './component/button-add/button-add.component';
import {ButtonSaveComponent} from './component/button-save/button-save.component';
import {ButtonReadComponent} from './component/button-read/button-read.component';
import {ButtonUpdateComponent} from './component/button-update/button-update.component';
import {ButtonDeleteComponent} from './component/button-delete/button-delete.component';
import {ButtonDeleteBunchComponent} from './component/button-delete-bunch/button-delete-bunch.component';
import {ButtonEditComponent} from './component/button-edit/button-edit.component';
import {ButtonIndexComponent} from './component/button-index/button-index.component';
import {ButtonNextComponent} from './component/button-next/button-next.component';
import {ButtonPreviousComponent} from './component/button-previous/button-previous.component';
import {ButtonRefreshComponent} from './component/button-refresh/button-refresh.component';
import {ButtonRefreshMenuComponent} from './component/button-refresh-menu/button-refresh-menu.component';
import {ButtonPhotoComponent} from './component/button-photo/button-photo.component';
import {ButtonCoffeeComponent} from './component/button-coffee/button-coffee.component';
import {GridDataComponent} from './component/grid-data/grid-data.component';
import {GridPhotoComponent} from './component/grid-photo/grid-photo.component';
import {GridIndexComponent} from './component/grid-index/grid-index.component';
import {CommonModule} from "@angular/common";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";


@NgModule({
  declarations: [
    PineComponentsUiComponent,
    ButtonAddComponent,
    ButtonSaveComponent,
    ButtonReadComponent,
    ButtonUpdateComponent,
    ButtonDeleteComponent,
    ButtonDeleteBunchComponent,
    ButtonEditComponent,
    ButtonIndexComponent,
    ButtonNextComponent,
    ButtonPreviousComponent,
    ButtonRefreshComponent,
    ButtonRefreshMenuComponent,
    ButtonPhotoComponent,
    ButtonCoffeeComponent,
    GridDataComponent,
    GridPhotoComponent,
    GridIndexComponent
  ],
  imports: [
    CommonModule,
    FontAwesomeModule
  ],
  exports: [PineComponentsUiComponent, GridDataComponent]
})
export class PineComponentsUiModule {
}
