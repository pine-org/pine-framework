import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {EmptyRouteComponent} from "./empty-route/empty-route.component";
import {ProductComponent} from './product/product.component';
import {PineComponentsUiModule} from 'pine-components-ui';

@NgModule({
  declarations: [
    AppComponent,
    EmptyRouteComponent,
    ProductComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    PineComponentsUiModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
