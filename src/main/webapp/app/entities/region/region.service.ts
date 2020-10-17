import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegion } from 'app/shared/model/region.model';
import { catchError } from 'rxjs/operators';
import { UtilsService } from '../../custom/utils/utils.service';

type EntityResponseType = HttpResponse<IRegion>;
type EntityArrayResponseType = HttpResponse<IRegion[]>;

@Injectable({ providedIn: 'root' })
export class RegionService {
  public resourceUrl = SERVER_API_URL + 'api/regions';

  constructor(protected http: HttpClient, protected utilsService: UtilsService) {}

  create(region: IRegion): Observable<EntityResponseType> {
    return this.http.post<IRegion>(this.resourceUrl, region, { observe: 'response' });
  }

  update(region: IRegion): Observable<EntityResponseType> {
    return this.http.put<IRegion>(this.resourceUrl, region, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  /* Retorna las regiones por pais */
  getByPais(idPais: number): Observable<IRegion[]> {
    return this.http.get<IRegion[]>(`${this.resourceUrl}/porpais/${idPais}`).pipe(
      catchError(e => {
        this.utilsService.popupError(e.error.mensaje);
        return throwError(e);
      })
    );
  }
}
