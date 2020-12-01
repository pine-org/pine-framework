import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

import {HttpClientModule} from "@angular/common/http";
import {HomeComponent} from './home/home.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {FormsModule} from "@angular/forms";
import {SaveButtonComponent} from './component/save-button/save-button.component';
import {AddButtonComponent} from './component/add-button/add-button.component';
import {ReadButtonComponent} from './component/read-button/read-button.component';
import {UpdateButtonComponent} from './component/update-button/update-button.component';
import {EditButtonComponent} from './component/edit-button/edit-button.component';
import {DeleteButtonComponent} from './component/delete-button/delete-button.component';
import {RefreshButtonComponent} from './component/refresh-button/refresh-button.component';
import {RefreshMenuButtonComponent} from './component/refresh-menu-button/refresh-menu-button.component';
import {NextButtonComponent} from './component/next-button/next-button.component';
import {PreviousButtonComponent} from './component/previous-button/previous-button.component';
import {IndexButtonComponent} from './component/index-button/index-button.component';
import {DataGridComponent} from './component/data-grid/data-grid.component';
import {DeleteBunchButtonComponent} from './component/delete-bunch-button/delete-bunch-button.component';
import {SlideButtonComponent} from './component/slide-button/slide-button.component';
import {PhotoAlbumComponent} from './component/photo-album/photo-album.component';
import {PageIndexComponent} from './component/page-index/page-index.component';
import {CoffeeButtonComponent} from './component/coffee-button/coffee-button.component';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        SaveButtonComponent,
        AddButtonComponent,
        ReadButtonComponent,
        UpdateButtonComponent,
        EditButtonComponent,
      DeleteButtonComponent,
      RefreshButtonComponent,
      RefreshMenuButtonComponent,
      NextButtonComponent,
      PreviousButtonComponent,
      IndexButtonComponent,
      DataGridComponent,
      DeleteBunchButtonComponent,
      SlideButtonComponent,
      PhotoAlbumComponent,
      PageIndexComponent,
      CoffeeButtonComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatIconModule,
        MatCardModule,
        MatButtonModule,
        MatProgressSpinnerModule,
        FormsModule,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
