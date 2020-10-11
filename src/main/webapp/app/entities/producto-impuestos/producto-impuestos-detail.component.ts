import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductoImpuestos } from 'app/shared/model/producto-impuestos.model';

@Component({
  selector: 'jhi-producto-impuestos-detail',
  templateUrl: './producto-impuestos-detail.component.html'
})
export class ProductoImpuestosDetailComponent implements OnInit {
  productoImpuestos: IProductoImpuestos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoImpuestos }) => {
      this.productoImpuestos = productoImpuestos;
    });
  }

  previousState() {
    window.history.back();
  }
}
