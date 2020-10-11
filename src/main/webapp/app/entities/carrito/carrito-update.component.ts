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
import { ICarrito, Carrito } from 'app/shared/model/carrito.model';
import { CarritoService } from './carrito.service';
import { IDireccion } from 'app/shared/model/direccion.model';
import { DireccionService } from 'app/entities/direccion/direccion.service';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona/persona.service';

@Component({
  selector: 'jhi-carrito-update',
  templateUrl: './carrito-update.component.html'
})
export class CarritoUpdateComponent implements OnInit {
  isSaving: boolean;

  direccions: IDireccion[];

  personas: IPersona[];

  editForm = this.fb.group({
    id: [],
    fechaPedido: [],
    fechaPago: [],
    fechaConfirmadoPago: [],
    fechaEntrega: [],
    estado: [],
    telefonoContacto: [],
    whatsapp: [],
    puntajeComprador: [],
    puntajeVendedor: [],
    fechaTermino: [],
    razonTermino: [],
    montoTotalCompra: [],
    montoDelivery: [],
    direccionDeliveryId: [],
    vendedorId: [],
    clienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carritoService: CarritoService,
    protected direccionService: DireccionService,
    protected personaService: PersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carrito }) => {
      this.updateForm(carrito);
    });
    this.direccionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDireccion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDireccion[]>) => response.body)
      )
      .subscribe((res: IDireccion[]) => (this.direccions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersona[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersona[]>) => response.body)
      )
      .subscribe((res: IPersona[]) => (this.personas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(carrito: ICarrito) {
    this.editForm.patchValue({
      id: carrito.id,
      fechaPedido: carrito.fechaPedido != null ? carrito.fechaPedido.format(DATE_TIME_FORMAT) : null,
      fechaPago: carrito.fechaPago != null ? carrito.fechaPago.format(DATE_TIME_FORMAT) : null,
      fechaConfirmadoPago: carrito.fechaConfirmadoPago != null ? carrito.fechaConfirmadoPago.format(DATE_TIME_FORMAT) : null,
      fechaEntrega: carrito.fechaEntrega != null ? carrito.fechaEntrega.format(DATE_TIME_FORMAT) : null,
      estado: carrito.estado,
      telefonoContacto: carrito.telefonoContacto,
      whatsapp: carrito.whatsapp,
      puntajeComprador: carrito.puntajeComprador,
      puntajeVendedor: carrito.puntajeVendedor,
      fechaTermino: carrito.fechaTermino != null ? carrito.fechaTermino.format(DATE_TIME_FORMAT) : null,
      razonTermino: carrito.razonTermino,
      montoTotalCompra: carrito.montoTotalCompra,
      montoDelivery: carrito.montoDelivery,
      direccionDeliveryId: carrito.direccionDeliveryId,
      vendedorId: carrito.vendedorId,
      clienteId: carrito.clienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carrito = this.createFromForm();
    if (carrito.id !== undefined) {
      this.subscribeToSaveResponse(this.carritoService.update(carrito));
    } else {
      this.subscribeToSaveResponse(this.carritoService.create(carrito));
    }
  }

  private createFromForm(): ICarrito {
    return {
      ...new Carrito(),
      id: this.editForm.get(['id']).value,
      fechaPedido:
        this.editForm.get(['fechaPedido']).value != null ? moment(this.editForm.get(['fechaPedido']).value, DATE_TIME_FORMAT) : undefined,
      fechaPago:
        this.editForm.get(['fechaPago']).value != null ? moment(this.editForm.get(['fechaPago']).value, DATE_TIME_FORMAT) : undefined,
      fechaConfirmadoPago:
        this.editForm.get(['fechaConfirmadoPago']).value != null
          ? moment(this.editForm.get(['fechaConfirmadoPago']).value, DATE_TIME_FORMAT)
          : undefined,
      fechaEntrega:
        this.editForm.get(['fechaEntrega']).value != null ? moment(this.editForm.get(['fechaEntrega']).value, DATE_TIME_FORMAT) : undefined,
      estado: this.editForm.get(['estado']).value,
      telefonoContacto: this.editForm.get(['telefonoContacto']).value,
      whatsapp: this.editForm.get(['whatsapp']).value,
      puntajeComprador: this.editForm.get(['puntajeComprador']).value,
      puntajeVendedor: this.editForm.get(['puntajeVendedor']).value,
      fechaTermino:
        this.editForm.get(['fechaTermino']).value != null ? moment(this.editForm.get(['fechaTermino']).value, DATE_TIME_FORMAT) : undefined,
      razonTermino: this.editForm.get(['razonTermino']).value,
      montoTotalCompra: this.editForm.get(['montoTotalCompra']).value,
      montoDelivery: this.editForm.get(['montoDelivery']).value,
      direccionDeliveryId: this.editForm.get(['direccionDeliveryId']).value,
      vendedorId: this.editForm.get(['vendedorId']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarrito>>) {
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

  trackDireccionById(index: number, item: IDireccion) {
    return item.id;
  }

  trackPersonaById(index: number, item: IPersona) {
    return item.id;
  }
}
