import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICarritoProducto, CarritoProducto } from 'app/shared/model/carrito-producto.model';
import { CarritoProductoService } from './carrito-producto.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { ICarrito } from 'app/shared/model/carrito.model';
import { CarritoService } from 'app/entities/carrito/carrito.service';

@Component({
  selector: 'jhi-carrito-producto-update',
  templateUrl: './carrito-producto-update.component.html'
})
export class CarritoProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  carritos: ICarrito[];

  editForm = this.fb.group({
    id: [],
    cantidad: [],
    precioCompra: [],
    estado: [],
    costoDelivery: [],
    productoId: [],
    carritoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carritoProductoService: CarritoProductoService,
    protected productoService: ProductoService,
    protected carritoService: CarritoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carritoProducto }) => {
      this.updateForm(carritoProducto);
    });
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.carritoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICarrito[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICarrito[]>) => response.body)
      )
      .subscribe((res: ICarrito[]) => (this.carritos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(carritoProducto: ICarritoProducto) {
    this.editForm.patchValue({
      id: carritoProducto.id,
      cantidad: carritoProducto.cantidad,
      precioCompra: carritoProducto.precioCompra,
      estado: carritoProducto.estado,
      costoDelivery: carritoProducto.costoDelivery,
      productoId: carritoProducto.productoId,
      carritoId: carritoProducto.carritoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carritoProducto = this.createFromForm();
    if (carritoProducto.id !== undefined) {
      this.subscribeToSaveResponse(this.carritoProductoService.update(carritoProducto));
    } else {
      this.subscribeToSaveResponse(this.carritoProductoService.create(carritoProducto));
    }
  }

  private createFromForm(): ICarritoProducto {
    return {
      ...new CarritoProducto(),
      id: this.editForm.get(['id']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      precioCompra: this.editForm.get(['precioCompra']).value,
      estado: this.editForm.get(['estado']).value,
      costoDelivery: this.editForm.get(['costoDelivery']).value,
      productoId: this.editForm.get(['productoId']).value,
      carritoId: this.editForm.get(['carritoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarritoProducto>>) {
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

  trackCarritoById(index: number, item: ICarrito) {
    return item.id;
  }
}
