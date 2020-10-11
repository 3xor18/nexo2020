import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoImpuestos } from 'app/shared/model/producto-impuestos.model';
import { ProductoImpuestosService } from './producto-impuestos.service';
import { ProductoImpuestosComponent } from './producto-impuestos.component';
import { ProductoImpuestosDetailComponent } from './producto-impuestos-detail.component';
import { ProductoImpuestosUpdateComponent } from './producto-impuestos-update.component';
import { ProductoImpuestosDeletePopupComponent } from './producto-impuestos-delete-dialog.component';
import { IProductoImpuestos } from 'app/shared/model/producto-impuestos.model';

@Injectable({ providedIn: 'root' })
export class ProductoImpuestosResolve implements Resolve<IProductoImpuestos> {
  constructor(private service: ProductoImpuestosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductoImpuestos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductoImpuestos>) => response.ok),
        map((productoImpuestos: HttpResponse<ProductoImpuestos>) => productoImpuestos.body)
      );
    }
    return of(new ProductoImpuestos());
  }
}

export const productoImpuestosRoute: Routes = [
  {
    path: '',
    component: ProductoImpuestosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImpuestos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductoImpuestosDetailComponent,
    resolve: {
      productoImpuestos: ProductoImpuestosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImpuestos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductoImpuestosUpdateComponent,
    resolve: {
      productoImpuestos: ProductoImpuestosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImpuestos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductoImpuestosUpdateComponent,
    resolve: {
      productoImpuestos: ProductoImpuestosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImpuestos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productoImpuestosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductoImpuestosDeletePopupComponent,
    resolve: {
      productoImpuestos: ProductoImpuestosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImpuestos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
