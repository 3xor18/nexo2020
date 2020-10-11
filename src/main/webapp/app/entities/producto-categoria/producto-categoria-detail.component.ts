import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductoCategoria } from 'app/shared/model/producto-categoria.model';

@Component({
  selector: 'jhi-producto-categoria-detail',
  templateUrl: './producto-categoria-detail.component.html'
})
export class ProductoCategoriaDetailComponent implements OnInit {
  productoCategoria: IProductoCategoria;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoCategoria }) => {
      this.productoCategoria = productoCategoria;
    });
  }

  previousState() {
    window.history.back();
  }
}
