import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarritoProducto } from 'app/shared/model/carrito-producto.model';

@Component({
  selector: 'jhi-carrito-producto-detail',
  templateUrl: './carrito-producto-detail.component.html'
})
export class CarritoProductoDetailComponent implements OnInit {
  carritoProducto: ICarritoProducto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carritoProducto }) => {
      this.carritoProducto = carritoProducto;
    });
  }

  previousState() {
    window.history.back();
  }
}
