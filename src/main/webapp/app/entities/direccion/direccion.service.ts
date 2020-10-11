import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDireccion } from 'app/shared/model/direccion.model';

type EntityResponseType = HttpResponse<IDireccion>;
type EntityArrayResponseType = HttpResponse<IDireccion[]>;

@Injectable({ providedIn: 'root' })
export class DireccionService {
  public resourceUrl = SERVER_API_URL + 'api/direccions';

  constructor(protected http: HttpClient) {}

  create(direccion: IDireccion): Observable<EntityResponseType> {
    return this.http.post<IDireccion>(this.resourceUrl, direccion, { observe: 'response' });
  }

  update(direccion: IDireccion): Observable<EntityResponseType> {
    return this.http.put<IDireccion>(this.resourceUrl, direccion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDireccion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDireccion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
