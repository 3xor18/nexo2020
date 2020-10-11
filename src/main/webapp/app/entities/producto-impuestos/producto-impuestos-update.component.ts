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
import { IProductoImpuestos, ProductoImpuestos } from 'app/shared/model/producto-impuestos.model';
import { ProductoImpuestosService } from './producto-impuestos.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';

@Component({
  selector: 'jhi-producto-impuestos-update',
  templateUrl: './producto-impuestos-update.component.html'
})
export class ProductoImpuestosUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    estado: [],
    nombre: [],
    fechaBd: [],
    impuestoMontoFijo: [],
    montoOTasa: [],
    impuestoNacional: [],
    fechaAplicable: [],
    productoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoImpuestosService: ProductoImpuestosService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productoImpuestos }) => {
      this.updateForm(productoImpuestos);
    });
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productoImpuestos: IProductoImpuestos) {
    this.editForm.patchValue({
      id: productoImpuestos.id,
      estado: productoImpuestos.estado,
      nombre: productoImpuestos.nombre,
      fechaBd: productoImpuestos.fechaBd != null ? productoImpuestos.fechaBd.format(DATE_TIME_FORMAT) : null,
      impuestoMontoFijo: productoImpuestos.impuestoMontoFijo,
      montoOTasa: productoImpuestos.montoOTasa,
      impuestoNacional: productoImpuestos.impuestoNacional,
      fechaAplicable: productoImpuestos.fechaAplicable != null ? productoImpuestos.fechaAplicable.format(DATE_TIME_FORMAT) : null,
      productoId: productoImpuestos.productoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productoImpuestos = this.createFromForm();
    if (productoImpuestos.id !== undefined) {
      this.subscribeToSaveResponse(this.productoImpuestosService.update(productoImpuestos));
    } else {
      this.subscribeToSaveResponse(this.productoImpuestosService.create(productoImpuestos));
    }
  }

  private createFromForm(): IProductoImpuestos {
    return {
      ...new ProductoImpuestos(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value,
      nombre: this.editForm.get(['nombre']).value,
      fechaBd: this.editForm.get(['fechaBd']).value != null ? moment(this.editForm.get(['fechaBd']).value, DATE_TIME_FORMAT) : undefined,
      impuestoMontoFijo: this.editForm.get(['impuestoMontoFijo']).value,
      montoOTasa: this.editForm.get(['montoOTasa']).value,
      impuestoNacional: this.editForm.get(['impuestoNacional']).value,
      fechaAplicable:
        this.editForm.get(['fechaAplicable']).value != null
          ? moment(this.editForm.get(['fechaAplicable']).value, DATE_TIME_FORMAT)
          : undefined,
      productoId: this.editForm.get(['productoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoImpuestos>>) {
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
}
