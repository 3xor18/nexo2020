import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoImagenes } from 'app/shared/model/producto-imagenes.model';
import { ProductoImagenesService } from './producto-imagenes.service';
import { ProductoImagenesComponent } from './producto-imagenes.component';
import { ProductoImagenesDetailComponent } from './producto-imagenes-detail.component';
import { ProductoImagenesUpdateComponent } from './producto-imagenes-update.component';
import { ProductoImagenesDeletePopupComponent } from './producto-imagenes-delete-dialog.component';
import { IProductoImagenes } from 'app/shared/model/producto-imagenes.model';

@Injectable({ providedIn: 'root' })
export class ProductoImagenesResolve implements Resolve<IProductoImagenes> {
  constructor(private service: ProductoImagenesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductoImagenes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductoImagenes>) => response.ok),
        map((productoImagenes: HttpResponse<ProductoImagenes>) => productoImagenes.body)
      );
    }
    return of(new ProductoImagenes());
  }
}

export const productoImagenesRoute: Routes = [
  {
    path: '',
    component: ProductoImagenesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImagenes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductoImagenesDetailComponent,
    resolve: {
      productoImagenes: ProductoImagenesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImagenes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductoImagenesUpdateComponent,
    resolve: {
      productoImagenes: ProductoImagenesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImagenes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductoImagenesUpdateComponent,
    resolve: {
      productoImagenes: ProductoImagenesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImagenes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productoImagenesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductoImagenesDeletePopupComponent,
    resolve: {
      productoImagenes: ProductoImagenesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoImagenes.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
