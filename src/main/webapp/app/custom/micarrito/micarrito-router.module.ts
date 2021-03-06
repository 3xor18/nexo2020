import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { UserRouteAccessService } from '../../core/auth/user-route-access-service';
import { IndexComponent } from './index/index.component';

const routes: Routes = [
  {
    path: 'micarrito',
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
  exports: [RouterModule]
})
export class MiCarritoRoutingModule {}
