import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { UserRouteAccessService } from '../../core/auth/user-route-access-service';
import { ListadoProductosComponent } from './componentes/listado/listado-productos.component';

/* Componentes */

const routes: Routes = [
  {
    path: 'productos',
    component: ListadoProductosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Productos en mi tienda'
    },
    children: [{ path: '', component: ListadoProductosComponent }],
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductosRoutingModule {}
