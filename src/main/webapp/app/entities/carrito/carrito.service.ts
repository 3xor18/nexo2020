import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICarrito } from 'app/shared/model/carrito.model';

type EntityResponseType = HttpResponse<ICarrito>;
type EntityArrayResponseType = HttpResponse<ICarrito[]>;

@Injectable({ providedIn: 'root' })
export class CarritoService {
  public resourceUrl = SERVER_API_URL + 'api/carritos';

  constructor(protected http: HttpClient) {}

  create(carrito: ICarrito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carrito);
    return this.http
      .post<ICarrito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(carrito: ICarrito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carrito);
    return this.http
      .put<ICarrito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICarrito>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICarrito[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(carrito: ICarrito): ICarrito {
    const copy: ICarrito = Object.assign({}, carrito, {
      fechaPedido: carrito.fechaPedido != null && carrito.fechaPedido.isValid() ? carrito.fechaPedido.toJSON() : null,
      fechaPago: carrito.fechaPago != null && carrito.fechaPago.isValid() ? carrito.fechaPago.toJSON() : null,
      fechaConfirmadoPago:
        carrito.fechaConfirmadoPago != null && carrito.fechaConfirmadoPago.isValid() ? carrito.fechaConfirmadoPago.toJSON() : null,
      fechaEntrega: carrito.fechaEntrega != null && carrito.fechaEntrega.isValid() ? carrito.fechaEntrega.toJSON() : null,
      fechaTermino: carrito.fechaTermino != null && carrito.fechaTermino.isValid() ? carrito.fechaTermino.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaPedido = res.body.fechaPedido != null ? moment(res.body.fechaPedido) : null;
      res.body.fechaPago = res.body.fechaPago != null ? moment(res.body.fechaPago) : null;
      res.body.fechaConfirmadoPago = res.body.fechaConfirmadoPago != null ? moment(res.body.fechaConfirmadoPago) : null;
      res.body.fechaEntrega = res.body.fechaEntrega != null ? moment(res.body.fechaEntrega) : null;
      res.body.fechaTermino = res.body.fechaTermino != null ? moment(res.body.fechaTermino) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((carrito: ICarrito) => {
        carrito.fechaPedido = carrito.fechaPedido != null ? moment(carrito.fechaPedido) : null;
        carrito.fechaPago = carrito.fechaPago != null ? moment(carrito.fechaPago) : null;
        carrito.fechaConfirmadoPago = carrito.fechaConfirmadoPago != null ? moment(carrito.fechaConfirmadoPago) : null;
        carrito.fechaEntrega = carrito.fechaEntrega != null ? moment(carrito.fechaEntrega) : null;
        carrito.fechaTermino = carrito.fechaTermino != null ? moment(carrito.fechaTermino) : null;
      });
    }
    return res;
  }
}
