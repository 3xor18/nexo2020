import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductoImagenes } from 'app/shared/model/producto-imagenes.model';

@Component({
  selector: 'jhi-producto-imagenes-detail',
  templateUrl: './producto-imagenes-detail.component.html'
})
export class ProductoImagenesDetailComponent implements OnInit {
  productoImagenes: IProductoImagenes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoImagenes }) => {
      this.productoImagenes = productoImagenes;
    });
  }

  previousState() {
    window.history.back();
  }
}
