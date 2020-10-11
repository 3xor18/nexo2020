import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarrito } from 'app/shared/model/carrito.model';

@Component({
  selector: 'jhi-carrito-detail',
  templateUrl: './carrito-detail.component.html'
})
export class CarritoDetailComponent implements OnInit {
  carrito: ICarrito;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carrito }) => {
      this.carrito = carrito;
    });
  }

  previousState() {
    window.history.back();
  }
}
