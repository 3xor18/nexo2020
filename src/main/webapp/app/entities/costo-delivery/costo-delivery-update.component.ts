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
import { ICostoDelivery, CostoDelivery } from 'app/shared/model/costo-delivery.model';
import { CostoDeliveryService } from './costo-delivery.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from 'app/entities/comuna/comuna.service';

@Component({
  selector: 'jhi-costo-delivery-update',
  templateUrl: './costo-delivery-update.component.html'
})
export class CostoDeliveryUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  comunas: IComuna[];

  editForm = this.fb.group({
    id: [],
    estado: [],
    montoIndividual: [],
    montoMayor: [],
    cantidadMayor: [],
    fechaBd: [],
    productoId: [],
    comunaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected costoDeliveryService: CostoDeliveryService,
    protected productoService: ProductoService,
    protected comunaService: ComunaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ costoDelivery }) => {
      this.updateForm(costoDelivery);
    });
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.comunaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IComuna[]>) => mayBeOk.ok),
        map((response: HttpResponse<IComuna[]>) => response.body)
      )
      .subscribe((res: IComuna[]) => (this.comunas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(costoDelivery: ICostoDelivery) {
    this.editForm.patchValue({
      id: costoDelivery.id,
      estado: costoDelivery.estado,
      montoIndividual: costoDelivery.montoIndividual,
      montoMayor: costoDelivery.montoMayor,
      cantidadMayor: costoDelivery.cantidadMayor,
      fechaBd: costoDelivery.fechaBd != null ? costoDelivery.fechaBd.format(DATE_TIME_FORMAT) : null,
      productoId: costoDelivery.productoId,
      comunaId: costoDelivery.comunaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const costoDelivery = this.createFromForm();
    if (costoDelivery.id !== undefined) {
      this.subscribeToSaveResponse(this.costoDeliveryService.update(costoDelivery));
    } else {
      this.subscribeToSaveResponse(this.costoDeliveryService.create(costoDelivery));
    }
  }

  private createFromForm(): ICostoDelivery {
    return {
      ...new CostoDelivery(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value,
      montoIndividual: this.editForm.get(['montoIndividual']).value,
      montoMayor: this.editForm.get(['montoMayor']).value,
      cantidadMayor: this.editForm.get(['cantidadMayor']).value,
      fechaBd: this.editForm.get(['fechaBd']).value != null ? moment(this.editForm.get(['fechaBd']).value, DATE_TIME_FORMAT) : undefined,
      productoId: this.editForm.get(['productoId']).value,
      comunaId: this.editForm.get(['comunaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICostoDelivery>>) {
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackComunaById(index: number, item: IComuna) {
    return item.id;
  }
}
