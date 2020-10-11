import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICostoDelivery } from 'app/shared/model/costo-delivery.model';

type EntityResponseType = HttpResponse<ICostoDelivery>;
type EntityArrayResponseType = HttpResponse<ICostoDelivery[]>;

@Injectable({ providedIn: 'root' })
export class CostoDeliveryService {
  public resourceUrl = SERVER_API_URL + 'api/costo-deliveries';

  constructor(protected http: HttpClient) {}

  create(costoDelivery: ICostoDelivery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(costoDelivery);
    return this.http
      .post<ICostoDelivery>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(costoDelivery: ICostoDelivery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(costoDelivery);
    return this.http
      .put<ICostoDelivery>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICostoDelivery>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICostoDelivery[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(costoDelivery: ICostoDelivery): ICostoDelivery {
    const copy: ICostoDelivery = Object.assign({}, costoDelivery, {
      fechaBd: costoDelivery.fechaBd != null && costoDelivery.fechaBd.isValid() ? costoDelivery.fechaBd.toJSON() : null
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
      res.body.forEach((costoDelivery: ICostoDelivery) => {
        costoDelivery.fechaBd = costoDelivery.fechaBd != null ? moment(costoDelivery.fechaBd) : null;
      });
    }
    return res;
  }
}
