import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { UserRouteAccessService } from '../../core/auth/user-route-access-service';

/* Componentes */
import { IndexComponent } from './index/index.component';

const routes: Routes = [
  {
    path: 'mitienda',
    component: IndexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Productos en mi tienda'
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MiTiendaRoutingModule {}
