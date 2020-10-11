import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CarritoProducto } from 'app/shared/model/carrito-producto.model';
import { CarritoProductoService } from './carrito-producto.service';
import { CarritoProductoComponent } from './carrito-producto.component';
import { CarritoProductoDetailComponent } from './carrito-producto-detail.component';
import { CarritoProductoUpdateComponent } from './carrito-producto-update.component';
import { CarritoProductoDeletePopupComponent } from './carrito-producto-delete-dialog.component';
import { ICarritoProducto } from 'app/shared/model/carrito-producto.model';

@Injectable({ providedIn: 'root' })
export class CarritoProductoResolve implements Resolve<ICarritoProducto> {
  constructor(private service: CarritoProductoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICarritoProducto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CarritoProducto>) => response.ok),
        map((carritoProducto: HttpResponse<CarritoProducto>) => carritoProducto.body)
      );
    }
    return of(new CarritoProducto());
  }
}

export const carritoProductoRoute: Routes = [
  {
    path: '',
    component: CarritoProductoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'nexoApp.carritoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarritoProductoDetailComponent,
    resolve: {
      carritoProducto: CarritoProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.carritoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarritoProductoUpdateComponent,
    resolve: {
      carritoProducto: CarritoProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.carritoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarritoProductoUpdateComponent,
    resolve: {
      carritoProducto: CarritoProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.carritoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const carritoProductoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CarritoProductoDeletePopupComponent,
    resolve: {
      carritoProducto: CarritoProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.carritoProducto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
