import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Carrito } from 'app/shared/model/carrito.model';
import { CarritoService } from './carrito.service';
import { CarritoComponent } from './carrito.component';
import { CarritoDetailComponent } from './carrito-detail.component';
import { CarritoUpdateComponent } from './carrito-update.component';
import { CarritoDeletePopupComponent } from './carrito-delete-dialog.component';
import { ICarrito } from 'app/shared/model/carrito.model';

@Injectable({ providedIn: 'root' })
export class CarritoResolve implements Resolve<ICarrito> {
  constructor(private service: CarritoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICarrito> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Carrito>) => response.ok),
        map((carrito: HttpResponse<Carrito>) => carrito.body)
      );
    }
    return of(new Carrito());
  }
}

export const carritoRoute: Routes = [
  {
    path: '',
    component: CarritoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'nexoApp.carrito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarritoDetailComponent,
    resolve: {
      carrito: CarritoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.carrito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarritoUpdateComponent,
    resolve: {
      carrito: CarritoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.carrito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarritoUpdateComponent,
    resolve: {
      carrito: CarritoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.carrito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const carritoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CarritoDeletePopupComponent,
    resolve: {
      carrito: CarritoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.carrito.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
