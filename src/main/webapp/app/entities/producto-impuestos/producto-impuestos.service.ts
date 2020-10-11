import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductoImpuestos } from 'app/shared/model/producto-impuestos.model';

type EntityResponseType = HttpResponse<IProductoImpuestos>;
type EntityArrayResponseType = HttpResponse<IProductoImpuestos[]>;

@Injectable({ providedIn: 'root' })
export class ProductoImpuestosService {
  public resourceUrl = SERVER_API_URL + 'api/producto-impuestos';

  constructor(protected http: HttpClient) {}

  create(productoImpuestos: IProductoImpuestos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoImpuestos);
    return this.http
      .post<IProductoImpuestos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productoImpuestos: IProductoImpuestos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoImpuestos);
    return this.http
      .put<IProductoImpuestos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductoImpuestos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductoImpuestos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(productoImpuestos: IProductoImpuestos): IProductoImpuestos {
    const copy: IProductoImpuestos = Object.assign({}, productoImpuestos, {
      fechaBd: productoImpuestos.fechaBd != null && productoImpuestos.fechaBd.isValid() ? productoImpuestos.fechaBd.toJSON() : null,
      fechaAplicable:
        productoImpuestos.fechaAplicable != null && productoImpuestos.fechaAplicable.isValid()
          ? productoImpuestos.fechaAplicable.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaBd = res.body.fechaBd != null ? moment(res.body.fechaBd) : null;
      res.body.fechaAplicable = res.body.fechaAplicable != null ? moment(res.body.fechaAplicable) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productoImpuestos: IProductoImpuestos) => {
        productoImpuestos.fechaBd = productoImpuestos.fechaBd != null ? moment(productoImpuestos.fechaBd) : null;
        productoImpuestos.fechaAplicable = productoImpuestos.fechaAplicable != null ? moment(productoImpuestos.fechaAplicable) : null;
      });
    }
    return res;
  }
}
