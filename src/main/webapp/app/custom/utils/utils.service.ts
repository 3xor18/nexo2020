import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import * as constantes from '../../app.constants';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {
  constructor() {}

  /* Popup de erro, si no se envia mensaje muestra por defaul error en el proeso */
  public popupError(mensajeIn?: string) {
    let mensaje = 'Error en el proceso';
    if (mensaje || mensaje.length > 0) {
      mensaje = mensajeIn;
    }
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: mensaje
    });
  }

  // Despliega un pop up para carga asíncrona
  public popupLoading(mensajeIn: string) {
    let mensaje = 'Cargando';
    if (mensaje || mensaje.length > 0) {
      mensaje = mensajeIn;
    }
    Swal.fire({
      icon: 'info',
      title: mensaje,
      showConfirmButton: false,
      timerProgressBar: false,
      onBeforeOpen() {
        Swal.showLoading();
      }
    });
  }

  /* Popup de Pregunta de seleccion, Para llamar a este metodo =await popupPregunta('titulo)*/
  async popupPregunta(titulo: string) {
    return await Swal.fire({
      title: '' + titulo,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'Cancelar',
      confirmButtonText: 'Aceptar',
      reverseButtons: true
    }).then(result => {
      if (result.value) {
        return constantes.SI;
      } else {
        return constantes.NO;
      }
    });
  }

  /* Popup con Input, para llamarlo =await popupWithIpunt('titulo') */
  async popupWithInput(titulo: string) {
    return await Swal.fire({
      title: '' + titulo,
      input: 'text'
    }).then(monto => {
      if (monto.value) {
        return monto.value;
      } else {
        return constantes.NO;
      }
    });
  }

  /* Formatea un numero */
  public formatNumber(amount: any, symbol = '$ ', decimalCount = 0, decimal = ',', thousands = '.') {
    try {
      amount = amount || 0;
      decimalCount = Math.abs(decimalCount);
      decimalCount = isNaN(decimalCount) ? 2 : decimalCount;
      const negativeSign = amount < 0 ? '-' : '';
      const i = parseInt((amount = Math.abs(Number(amount) || 0).toFixed(decimalCount)), 10).toString();
      const j = i.length > 3 ? i.length % 3 : 0;
      return (
        negativeSign +
        symbol +
        (j ? i.substr(0, j) + thousands : '') +
        i.substr(j).replace(/(\d{3})(?=\d)/g, '$1' + thousands) +
        (decimalCount
          ? decimal +
            Math.abs(amount - parseInt(i, 10))
              .toFixed(decimalCount)
              .slice(2)
          : '')
      );
    } catch (e) {
      // error
    }
    return amount;
  }

  /* popup d info */
  async popupInfo(mensaje?: string) {
    return await Swal.fire(mensaje).then(x => 'OK');
  }

  /* Quita los puntos */
  quitarPuntos(valor: string) {
    return valor.replace('.', '');
  }

  /* remplaza las comas */
  remplazarPuntosPorComas(valor: string) {
    return valor.replace(',', '.');
  }

  /* deja en formato BD el monto */
  formatearMontoToSave(monto: string) {
    monto = this.quitarPuntos(monto);
    monto = this.remplazarPuntosPorComas(monto);
    return monto;
  }
}
