import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { ProductoComponent } from './producto.component';
import { ProductoDetailComponent } from './producto-detail.component';
import { ProductoUpdateComponent } from './producto-update.component';
import { ProductoDeletePopupComponent } from './producto-delete-dialog.component';
import { IProducto } from 'app/shared/model/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoResolve implements Resolve<IProducto> {
  constructor(private service: ProductoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProducto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Producto>) => response.ok),
        map((producto: HttpResponse<Producto>) => producto.body)
      );
    }
    return of(new Producto());
  }
}

export const productoRoute: Routes = [
  {
    path: '',
    component: ProductoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'nexoApp.producto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductoDetailComponent,
    resolve: {
      producto: ProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.producto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductoUpdateComponent,
    resolve: {
      producto: ProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.producto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductoUpdateComponent,
    resolve: {
      producto: ProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.producto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductoDeletePopupComponent,
    resolve: {
      producto: ProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.producto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
