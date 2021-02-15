import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {APP_BASE_HREF} from "@angular/common";
import {EmptyRouteComponent} from "./empty-route/empty-route.component";
import {ProductComponent} from "./product/product.component";

const routes: Routes = [
  {
    path: 'eshop',
    children: [
      {
        path: 'product',
        component: ProductComponent
      }
    ]
  },
  {
    path: '**',
    component: EmptyRouteComponent
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [{provide: APP_BASE_HREF, useValue: '/'}]
})
export class AppRoutingModule {
}
