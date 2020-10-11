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
import { IProductoImagenes, ProductoImagenes } from 'app/shared/model/producto-imagenes.model';
import { ProductoImagenesService } from './producto-imagenes.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';

@Component({
  selector: 'jhi-producto-imagenes-update',
  templateUrl: './producto-imagenes-update.component.html'
})
export class ProductoImagenesUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    estado: [],
    fechaBd: [],
    path: [],
    nombre: [],
    productoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoImagenesService: ProductoImagenesService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productoImagenes }) => {
      this.updateForm(productoImagenes);
    });
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productoImagenes: IProductoImagenes) {
    this.editForm.patchValue({
      id: productoImagenes.id,
      estado: productoImagenes.estado,
      fechaBd: productoImagenes.fechaBd != null ? productoImagenes.fechaBd.format(DATE_TIME_FORMAT) : null,
      path: productoImagenes.path,
      nombre: productoImagenes.nombre,
      productoId: productoImagenes.productoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productoImagenes = this.createFromForm();
    if (productoImagenes.id !== undefined) {
      this.subscribeToSaveResponse(this.productoImagenesService.update(productoImagenes));
    } else {
      this.subscribeToSaveResponse(this.productoImagenesService.create(productoImagenes));
    }
  }

  private createFromForm(): IProductoImagenes {
    return {
      ...new ProductoImagenes(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value,
      fechaBd: this.editForm.get(['fechaBd']).value != null ? moment(this.editForm.get(['fechaBd']).value, DATE_TIME_FORMAT) : undefined,
      path: this.editForm.get(['path']).value,
      nombre: this.editForm.get(['nombre']).value,
      productoId: this.editForm.get(['productoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoImagenes>>) {
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
