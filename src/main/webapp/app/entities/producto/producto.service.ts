import { EventEmitter, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map, catchError } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProducto } from 'app/shared/model/producto.model';
import { UtilsService } from '../../custom/utils/utils.service';

type EntityResponseType = HttpResponse<IProducto>;
type EntityArrayResponseType = HttpResponse<IProducto[]>;

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private _notificarCambio = new EventEmitter<IProducto>();

  public resourceUrl = SERVER_API_URL + 'api/productos';

  constructor(protected http: HttpClient, protected utilsService: UtilsService) {}

  get notificarCambio(): EventEmitter<IProducto> {
    return this._notificarCambio;
  }

  create(producto: IProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(producto);
    return this.http
      .post<IProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(producto: IProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(producto);
    return this.http
      .put<IProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProducto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(producto: IProducto): IProducto {
    const copy: IProducto = Object.assign({}, producto, {
      fechaVencimiento:
        producto.fechaVencimiento != null && producto.fechaVencimiento.isValid() ? producto.fechaVencimiento.format(DATE_FORMAT) : null,
      fechaBd: producto.fechaBd != null && producto.fechaBd.isValid() ? producto.fechaBd.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaVencimiento = res.body.fechaVencimiento != null ? moment(res.body.fechaVencimiento) : null;
      res.body.fechaBd = res.body.fechaBd != null ? moment(res.body.fechaBd) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((producto: IProducto) => {
        producto.fechaVencimiento = producto.fechaVencimiento != null ? moment(producto.fechaVencimiento) : null;
        producto.fechaBd = producto.fechaBd != null ? moment(producto.fechaBd) : null;
      });
    }
    return res;
  }

  getMyProducts(page: number): Observable<any> {
    return this.http.get<IProducto[]>(`${this.resourceUrl}/myproducts/${page}`).pipe(
      catchError(e => {
        this.utilsService.popupError(e.error.mensaje);
        return throwError(e);
      })
    );
  }

  /* Crea un nuevo producto */
  crearProducto(producto: IProducto): Observable<IProducto> {
    return this.http.post<IProducto>(`${this.resourceUrl}/newproduct`, producto).pipe(
      catchError(e => {
        this.utilsService.popupError(e.error.mensaje);
        return throwError(e);
      })
    );
  }
}
