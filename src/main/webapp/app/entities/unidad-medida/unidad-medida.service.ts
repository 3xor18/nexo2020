import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';

type EntityResponseType = HttpResponse<IUnidadMedida>;
type EntityArrayResponseType = HttpResponse<IUnidadMedida[]>;

@Injectable({ providedIn: 'root' })
export class UnidadMedidaService {
  public resourceUrl = SERVER_API_URL + 'api/unidad-medidas';

  constructor(protected http: HttpClient) {}

  create(unidadMedida: IUnidadMedida): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(unidadMedida);
    return this.http
      .post<IUnidadMedida>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(unidadMedida: IUnidadMedida): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(unidadMedida);
    return this.http
      .put<IUnidadMedida>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUnidadMedida>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUnidadMedida[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(unidadMedida: IUnidadMedida): IUnidadMedida {
    const copy: IUnidadMedida = Object.assign({}, unidadMedida, {
      fechaBd: unidadMedida.fechaBd != null && unidadMedida.fechaBd.isValid() ? unidadMedida.fechaBd.toJSON() : null
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
      res.body.forEach((unidadMedida: IUnidadMedida) => {
        unidadMedida.fechaBd = unidadMedida.fechaBd != null ? moment(unidadMedida.fechaBd) : null;
      });
    }
    return res;
  }
}
