import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona/persona.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais/pais.service';
import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from 'app/entities/comuna/comuna.service';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from 'app/entities/unidad-medida/unidad-medida.service';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  personas: IPersona[];

  pais: IPais[];

  comunas: IComuna[];

  unidadmedidas: IUnidadMedida[];
  fechaVencimientoDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    codigo: [],
    codigoBarra: [],
    descripcion: [],
    cantidadDisponible: [],
    alertaMinimo: [],
    fechaVencimiento: [],
    fechaBd: [],
    estado: [],
    precioCompraBruto: [],
    precioVentaTotalDetal: [],
    precioVentaTotalMayor: [],
    unidadMedidaVendida: [],
    precioAlmayorDespuesde: [],
    vendedorId: [],
    elaboradoEnId: [],
    comunaVentaId: [],
    unidadMedidaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected personaService: PersonaService,
    protected paisService: PaisService,
    protected comunaService: ComunaService,
    protected unidadMedidaService: UnidadMedidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
    });
    this.personaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersona[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersona[]>) => response.body)
      )
      .subscribe((res: IPersona[]) => (this.personas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.paisService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPais[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPais[]>) => response.body)
      )
      .subscribe((res: IPais[]) => (this.pais = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.comunaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IComuna[]>) => mayBeOk.ok),
        map((response: HttpResponse<IComuna[]>) => response.body)
      )
      .subscribe((res: IComuna[]) => (this.comunas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.unidadMedidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUnidadMedida[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUnidadMedida[]>) => response.body)
      )
      .subscribe((res: IUnidadMedida[]) => (this.unidadmedidas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      codigo: producto.codigo,
      codigoBarra: producto.codigoBarra,
      descripcion: producto.descripcion,
      cantidadDisponible: producto.cantidadDisponible,
      alertaMinimo: producto.alertaMinimo,
      fechaVencimiento: producto.fechaVencimiento,
      fechaBd: producto.fechaBd != null ? producto.fechaBd.format(DATE_TIME_FORMAT) : null,
      estado: producto.estado,
      precioCompraBruto: producto.precioCompraBruto,
      precioVentaTotalDetal: producto.precioVentaTotalDetal,
      precioVentaTotalMayor: producto.precioVentaTotalMayor,
      unidadMedidaVendida: producto.unidadMedidaVendida,
      precioAlmayorDespuesde: producto.precioAlmayorDespuesde,
      vendedorId: producto.vendedorId,
      elaboradoEnId: producto.elaboradoEnId,
      comunaVentaId: producto.comunaVentaId,
      unidadMedidaId: producto.unidadMedidaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      codigo: this.editForm.get(['codigo']).value,
      codigoBarra: this.editForm.get(['codigoBarra']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      cantidadDisponible: this.editForm.get(['cantidadDisponible']).value,
      alertaMinimo: this.editForm.get(['alertaMinimo']).value,
      fechaVencimiento: this.editForm.get(['fechaVencimiento']).value,
      fechaBd: this.editForm.get(['fechaBd']).value != null ? moment(this.editForm.get(['fechaBd']).value, DATE_TIME_FORMAT) : undefined,
      estado: this.editForm.get(['estado']).value,
      precioCompraBruto: this.editForm.get(['precioCompraBruto']).value,
      precioVentaTotalDetal: this.editForm.get(['precioVentaTotalDetal']).value,
      precioVentaTotalMayor: this.editForm.get(['precioVentaTotalMayor']).value,
      unidadMedidaVendida: this.editForm.get(['unidadMedidaVendida']).value,
      precioAlmayorDespuesde: this.editForm.get(['precioAlmayorDespuesde']).value,
      vendedorId: this.editForm.get(['vendedorId']).value,
      elaboradoEnId: this.editForm.get(['elaboradoEnId']).value,
      comunaVentaId: this.editForm.get(['comunaVentaId']).value,
      unidadMedidaId: this.editForm.get(['unidadMedidaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPersonaById(index: number, item: IPersona) {
    return item.id;
  }

  trackPaisById(index: number, item: IPais) {
    return item.id;
  }

  trackComunaById(index: number, item: IComuna) {
    return item.id;
  }

  trackUnidadMedidaById(index: number, item: IUnidadMedida) {
    return item.id;
  }
}
