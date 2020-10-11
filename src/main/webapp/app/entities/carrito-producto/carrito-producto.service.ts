import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICarritoProducto } from 'app/shared/model/carrito-producto.model';

type EntityResponseType = HttpResponse<ICarritoProducto>;
type EntityArrayResponseType = HttpResponse<ICarritoProducto[]>;

@Injectable({ providedIn: 'root' })
export class CarritoProductoService {
  public resourceUrl = SERVER_API_URL + 'api/carrito-productos';

  constructor(protected http: HttpClient) {}

  create(carritoProducto: ICarritoProducto): Observable<EntityResponseType> {
    return this.http.post<ICarritoProducto>(this.resourceUrl, carritoProducto, { observe: 'response' });
  }

  update(carritoProducto: ICarritoProducto): Observable<EntityResponseType> {
    return this.http.put<ICarritoProducto>(this.resourceUrl, carritoProducto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarritoProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarritoProducto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
