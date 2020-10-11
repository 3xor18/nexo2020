import { IndexComponent } from './index/index.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { UserRouteAccessService } from '../../core/auth/user-route-access-service';

const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Productos en mi carrito'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: '',
    component: IndexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Productos en mi carrito'
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: [IndexComponent]
})
export class MiCarritoRoutingModule {}
