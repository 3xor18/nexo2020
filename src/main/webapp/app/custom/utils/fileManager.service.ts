import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { UtilsService } from './utils.service';
import { map, catchError } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class FileManagerService {
  public resourceUrl = SERVER_API_URL + 'api/files';

  constructor(private http: HttpClient, protected utilsService: UtilsService) {}

  /* Descarga el File del documento (factura) desde el aws al pc actual */
  downloadImagenProducto(idFile: number): Observable<any> {
    return this.http.get(`${this.resourceUrl}/descargarimagenproducto/${idFile}`, { responseType: 'blob' }).pipe(
      catchError(e => {
        this.utilsService.popupError(e.error.mensaje);
        return throwError(e);
      })
    );
  }

  /* guarda el File de un documento */
  uploadFile(file: File, razon: string): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('razon', razon);
    return this.http.post<string>(`${this.resourceUrl}`, formData).pipe(
      map((response: any) => response.path as string),
      catchError(e => {
        this.utilsService.popupError(e.error.mensaje);
        return throwError(e);
      })
    );
  }
}
