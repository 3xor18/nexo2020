import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductoImagenes } from 'app/shared/model/producto-imagenes.model';

type EntityResponseType = HttpResponse<IProductoImagenes>;
type EntityArrayResponseType = HttpResponse<IProductoImagenes[]>;

@Injectable({ providedIn: 'root' })
export class ProductoImagenesService {
  public resourceUrl = SERVER_API_URL + 'api/producto-imagenes';

  constructor(protected http: HttpClient) {}

  create(productoImagenes: IProductoImagenes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoImagenes);
    return this.http
      .post<IProductoImagenes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productoImagenes: IProductoImagenes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoImagenes);
    return this.http
      .put<IProductoImagenes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductoImagenes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductoImagenes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(productoImagenes: IProductoImagenes): IProductoImagenes {
    const copy: IProductoImagenes = Object.assign({}, productoImagenes, {
      fechaBd: productoImagenes.fechaBd != null && productoImagenes.fechaBd.isValid() ? productoImagenes.fechaBd.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaBd = res.body.fechaBd != null ? moment(res.body.fechaBd) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productoImagenes: IProductoImagenes) => {
        productoImagenes.fechaBd = productoImagenes.fechaBd != null ? moment(productoImagenes.fechaBd) : null;
      });
    }
    return res;
  }
}
