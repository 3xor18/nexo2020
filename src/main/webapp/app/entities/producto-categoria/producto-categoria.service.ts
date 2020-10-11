import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductoCategoria } from 'app/shared/model/producto-categoria.model';

type EntityResponseType = HttpResponse<IProductoCategoria>;
type EntityArrayResponseType = HttpResponse<IProductoCategoria[]>;

@Injectable({ providedIn: 'root' })
export class ProductoCategoriaService {
  public resourceUrl = SERVER_API_URL + 'api/producto-categorias';

  constructor(protected http: HttpClient) {}

  create(productoCategoria: IProductoCategoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoCategoria);
    return this.http
      .post<IProductoCategoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productoCategoria: IProductoCategoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoCategoria);
    return this.http
      .put<IProductoCategoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductoCategoria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductoCategoria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(productoCategoria: IProductoCategoria): IProductoCategoria {
    const copy: IProductoCategoria = Object.assign({}, productoCategoria, {
      fechaBD: productoCategoria.fechaBD != null && productoCategoria.fechaBD.isValid() ? productoCategoria.fechaBD.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaBD = res.body.fechaBD != null ? moment(res.body.fechaBD) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productoCategoria: IProductoCategoria) => {
        productoCategoria.fechaBD = productoCategoria.fechaBD != null ? moment(productoCategoria.fechaBD) : null;
      });
    }
    return res;
  }
}
