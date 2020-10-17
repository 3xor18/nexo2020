import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IComuna } from 'app/shared/model/comuna.model';
import { catchError } from 'rxjs/operators';
import { UtilsService } from '../../custom/utils/utils.service';

type EntityResponseType = HttpResponse<IComuna>;
type EntityArrayResponseType = HttpResponse<IComuna[]>;

@Injectable({ providedIn: 'root' })
export class ComunaService {
  public resourceUrl = SERVER_API_URL + 'api/comunas';

  constructor(protected http: HttpClient, protected utilsService: UtilsService) {}

  create(comuna: IComuna): Observable<EntityResponseType> {
    return this.http.post<IComuna>(this.resourceUrl, comuna, { observe: 'response' });
  }

  update(comuna: IComuna): Observable<EntityResponseType> {
    return this.http.put<IComuna>(this.resourceUrl, comuna, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComuna>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComuna[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  /* Retorna las Comunas por Region */
  getByRegion(idRegion: number): Observable<IComuna[]> {
    return this.http.get<IComuna[]>(`${this.resourceUrl}/porregion/${idRegion}`).pipe(
      catchError(e => {
        this.utilsService.popupError(e.error.mensaje);
        return throwError(e);
      })
    );
  }
}
