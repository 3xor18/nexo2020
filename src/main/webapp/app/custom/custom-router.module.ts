import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { UserRouteAccessService } from '../core/auth/user-route-access-service';

const routes: Routes = [
  {
    path: '',
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Productos en mi tienda'
    },
    redirectTo: '/productos',
    pathMatch: 'full',
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomRoutingModule {}
