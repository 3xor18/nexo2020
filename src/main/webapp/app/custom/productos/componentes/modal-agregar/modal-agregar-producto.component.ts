import { Component, OnInit, ViewChild } from '@angular/core';
import { PaisService } from '../../../../entities/pais/pais.service';
import { IPais } from '../../../../shared/model/pais.model';
import { HttpResponse } from '@angular/common/http';
import { map, filter } from 'rxjs/operators';
import { UnidadMedidaService } from '../../../../entities/unidad-medida/unidad-medida.service';
import { IUnidadMedida } from '../../../../shared/model/unidad-medida.model';
import { DatosBasicosComponent } from './datos-basicos/datos-basicos.component';
import { DatosDeliveryComponent } from './datos-delivery/datos-delivery.component';
import * as constantes from '../../../../app.constants';
import { UtilsService } from '../../../utils/utils.service';
@Component({
  selector: 'jhi-modal-agregar-producto',
  templateUrl: './modal-agregar-producto.component.html',
  styles: []
})
export class ModalAgregarProductoComponent implements OnInit {
  @ViewChild(DatosBasicosComponent, { static: false }) datosBasicos: DatosBasicosComponent;
  @ViewChild(DatosDeliveryComponent, { static: false }) datosDelivery: DatosDeliveryComponent;

  show: boolean;
  paises: IPais[] = [];
  unidadesMedidas: IUnidadMedida[] = [];
  consultaDone = false;
  seccion: number;

  REGRESAR = 'REGRESAR';
  SEGUIR = 'SEGUIR';

  constructor(
    protected paisService: PaisService,
    protected unidadesMedidaService: UnidadMedidaService,
    protected utilsService: UtilsService
  ) {}

  ngOnInit() {
    this.seccion = 1;
    this.load();
  }

  /* Carga los datos para el form */
  load() {
    /* para el pais */
    this.paisService
      .query()
      .pipe(
        filter((res: HttpResponse<IPais[]>) => res.ok),
        map((res: HttpResponse<IPais[]>) => res.body)
      )
      .subscribe((res: IPais[]) => {
        this.paises = res;
      });
    /* Datos para unidad de medida */
    this.unidadesMedidaService
      .query()
      .pipe(
        filter((res: HttpResponse<IUnidadMedida[]>) => res.ok),
        map((res: HttpResponse<IUnidadMedida[]>) => res.body)
      )
      .subscribe((res: IUnidadMedida[]) => {
        this.unidadesMedidas = res;
        this.consultaDone = true;
      });
  }

  /* Abre el modal */
  public toggle() {
    this.show = !this.show;
  }

  /* Cambia de formularios */
  cambio(instruccion: string) {
    if (instruccion === this.SEGUIR) {
      this.seccion++;
    } else {
      this.seccion--;
    }
  }

  agregarDelivery(res: string) {
    const respuesta = res;
    if (respuesta === constantes.SI) {
      this.seccion++;
    } else {
      this.preguntarAgregarImpuestos();
    }
  }

  async preguntarAgregarImpuestos() {
    const resp = await this.utilsService.popupPregunta('¿Desea agregar cargos por delivery?');
    if (resp === constantes.SI) {
      this.seccion++;
    } else {
      this.toggle();
    }
  }
}
