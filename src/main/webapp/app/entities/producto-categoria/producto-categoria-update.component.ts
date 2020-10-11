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
import { IProductoCategoria, ProductoCategoria } from 'app/shared/model/producto-categoria.model';
import { ProductoCategoriaService } from './producto-categoria.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria/categoria.service';

@Component({
  selector: 'jhi-producto-categoria-update',
  templateUrl: './producto-categoria-update.component.html'
})
export class ProductoCategoriaUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  categorias: ICategoria[];

  editForm = this.fb.group({
    id: [],
    estado: [],
    fechaBD: [],
    productoId: [],
    categoriaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoCategoriaService: ProductoCategoriaService,
    protected productoService: ProductoService,
    protected categoriaService: CategoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productoCategoria }) => {
      this.updateForm(productoCategoria);
    });
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.categoriaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoria[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoria[]>) => response.body)
      )
      .subscribe((res: ICategoria[]) => (this.categorias = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productoCategoria: IProductoCategoria) {
    this.editForm.patchValue({
      id: productoCategoria.id,
      estado: productoCategoria.estado,
      fechaBD: productoCategoria.fechaBD != null ? productoCategoria.fechaBD.format(DATE_TIME_FORMAT) : null,
      productoId: productoCategoria.productoId,
      categoriaId: productoCategoria.categoriaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productoCategoria = this.createFromForm();
    if (productoCategoria.id !== undefined) {
      this.subscribeToSaveResponse(this.productoCategoriaService.update(productoCategoria));
    } else {
      this.subscribeToSaveResponse(this.productoCategoriaService.create(productoCategoria));
    }
  }

  private createFromForm(): IProductoCategoria {
    return {
      ...new ProductoCategoria(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value,
      fechaBD: this.editForm.get(['fechaBD']).value != null ? moment(this.editForm.get(['fechaBD']).value, DATE_TIME_FORMAT) : undefined,
      productoId: this.editForm.get(['productoId']).value,
      categoriaId: this.editForm.get(['categoriaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoCategoria>>) {
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

  trackCategoriaById(index: number, item: ICategoria) {
    return item.id;
  }
}
