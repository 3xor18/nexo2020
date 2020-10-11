import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Comuna } from 'app/shared/model/comuna.model';
import { ComunaService } from './comuna.service';
import { ComunaComponent } from './comuna.component';
import { ComunaDetailComponent } from './comuna-detail.component';
import { ComunaUpdateComponent } from './comuna-update.component';
import { ComunaDeletePopupComponent } from './comuna-delete-dialog.component';
import { IComuna } from 'app/shared/model/comuna.model';

@Injectable({ providedIn: 'root' })
export class ComunaResolve implements Resolve<IComuna> {
  constructor(private service: ComunaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComuna> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Comuna>) => response.ok),
        map((comuna: HttpResponse<Comuna>) => comuna.body)
      );
    }
    return of(new Comuna());
  }
}

export const comunaRoute: Routes = [
  {
    path: '',
    component: ComunaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.comuna.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComunaDetailComponent,
    resolve: {
      comuna: ComunaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.comuna.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComunaUpdateComponent,
    resolve: {
      comuna: ComunaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.comuna.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComunaUpdateComponent,
    resolve: {
      comuna: ComunaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.comuna.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const comunaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ComunaDeletePopupComponent,
    resolve: {
      comuna: ComunaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.comuna.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
