import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';

@Component({
  selector: 'jhi-unidad-medida-detail',
  templateUrl: './unidad-medida-detail.component.html'
})
export class UnidadMedidaDetailComponent implements OnInit {
  unidadMedida: IUnidadMedida;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ unidadMedida }) => {
      this.unidadMedida = unidadMedida;
    });
  }

  previousState() {
    window.history.back();
  }
}
