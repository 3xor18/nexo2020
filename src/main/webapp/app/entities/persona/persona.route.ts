import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Persona } from 'app/shared/model/persona.model';
import { PersonaService } from './persona.service';
import { PersonaComponent } from './persona.component';
import { PersonaDetailComponent } from './persona-detail.component';
import { PersonaUpdateComponent } from './persona-update.component';
import { PersonaDeletePopupComponent } from './persona-delete-dialog.component';
import { IPersona } from 'app/shared/model/persona.model';

@Injectable({ providedIn: 'root' })
export class PersonaResolve implements Resolve<IPersona> {
  constructor(private service: PersonaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersona> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Persona>) => response.ok),
        map((persona: HttpResponse<Persona>) => persona.body)
      );
    }
    return of(new Persona());
  }
}

export const personaRoute: Routes = [
  {
    path: '',
    component: PersonaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.persona.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonaDetailComponent,
    resolve: {
      persona: PersonaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.persona.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonaUpdateComponent,
    resolve: {
      persona: PersonaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.persona.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonaUpdateComponent,
    resolve: {
      persona: PersonaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.persona.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const personaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PersonaDeletePopupComponent,
    resolve: {
      persona: PersonaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nexoApp.persona.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
