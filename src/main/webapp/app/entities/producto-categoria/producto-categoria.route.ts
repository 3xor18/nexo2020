import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoCategoria } from 'app/shared/model/producto-categoria.model';
import { ProductoCategoriaService } from './producto-categoria.service';
import { ProductoCategoriaComponent } from './producto-categoria.component';
import { ProductoCategoriaDetailComponent } from './producto-categoria-detail.component';
import { ProductoCategoriaUpdateComponent } from './producto-categoria-update.component';
import { ProductoCategoriaDeletePopupComponent } from './producto-categoria-delete-dialog.component';
import { IProductoCategoria } from 'app/shared/model/producto-categoria.model';

@Injectable({ providedIn: 'root' })
export class ProductoCategoriaResolve implements Resolve<IProductoCategoria> {
  constructor(private service: ProductoCategoriaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductoCategoria> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductoCategoria>) => response.ok),
        map((productoCategoria: HttpResponse<ProductoCategoria>) => productoCategoria.body)
      );
    }
    return of(new ProductoCategoria());
  }
}

export const productoCategoriaRoute: Routes = [
  {
    path: '',
    component: ProductoCategoriaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductoCategoriaDetailComponent,
    resolve: {
      productoCategoria: ProductoCategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductoCategoriaUpdateComponent,
    resolve: {
      productoCategoria: ProductoCategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductoCategoriaUpdateComponent,
    resolve: {
      productoCategoria: ProductoCategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productoCategoriaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductoCategoriaDeletePopupComponent,
    resolve: {
      productoCategoria: ProductoCategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.productoCategoria.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
