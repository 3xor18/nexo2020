import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CostoDelivery } from 'app/shared/model/costo-delivery.model';
import { CostoDeliveryService } from './costo-delivery.service';
import { CostoDeliveryComponent } from './costo-delivery.component';
import { CostoDeliveryDetailComponent } from './costo-delivery-detail.component';
import { CostoDeliveryUpdateComponent } from './costo-delivery-update.component';
import { CostoDeliveryDeletePopupComponent } from './costo-delivery-delete-dialog.component';
import { ICostoDelivery } from 'app/shared/model/costo-delivery.model';

@Injectable({ providedIn: 'root' })
export class CostoDeliveryResolve implements Resolve<ICostoDelivery> {
  constructor(private service: CostoDeliveryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICostoDelivery> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CostoDelivery>) => response.ok),
        map((costoDelivery: HttpResponse<CostoDelivery>) => costoDelivery.body)
      );
    }
    return of(new CostoDelivery());
  }
}

export const costoDeliveryRoute: Routes = [
  {
    path: '',
    component: CostoDeliveryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.costoDelivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CostoDeliveryDetailComponent,
    resolve: {
      costoDelivery: CostoDeliveryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.costoDelivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CostoDeliveryUpdateComponent,
    resolve: {
      costoDelivery: CostoDeliveryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.costoDelivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CostoDeliveryUpdateComponent,
    resolve: {
      costoDelivery: CostoDeliveryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.costoDelivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const costoDeliveryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CostoDeliveryDeletePopupComponent,
    resolve: {
      costoDelivery: CostoDeliveryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.costoDelivery.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
