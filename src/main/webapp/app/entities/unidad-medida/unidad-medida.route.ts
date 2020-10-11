import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from './unidad-medida.service';
import { UnidadMedidaComponent } from './unidad-medida.component';
import { UnidadMedidaDetailComponent } from './unidad-medida-detail.component';
import { UnidadMedidaUpdateComponent } from './unidad-medida-update.component';
import { UnidadMedidaDeletePopupComponent } from './unidad-medida-delete-dialog.component';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';

@Injectable({ providedIn: 'root' })
export class UnidadMedidaResolve implements Resolve<IUnidadMedida> {
  constructor(private service: UnidadMedidaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUnidadMedida> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UnidadMedida>) => response.ok),
        map((unidadMedida: HttpResponse<UnidadMedida>) => unidadMedida.body)
      );
    }
    return of(new UnidadMedida());
  }
}

export const unidadMedidaRoute: Routes = [
  {
    path: '',
    component: UnidadMedidaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.unidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UnidadMedidaDetailComponent,
    resolve: {
      unidadMedida: UnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.unidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnidadMedidaUpdateComponent,
    resolve: {
      unidadMedida: UnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.unidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnidadMedidaUpdateComponent,
    resolve: {
      unidadMedida: UnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.unidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const unidadMedidaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UnidadMedidaDeletePopupComponent,
    resolve: {
      unidadMedida: UnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.unidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
