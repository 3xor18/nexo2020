import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDireccion } from 'app/shared/model/direccion.model';

@Component({
  selector: 'jhi-direccion-detail',
  templateUrl: './direccion-detail.component.html'
})
export class DireccionDetailComponent implements OnInit {
  direccion: IDireccion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ direccion }) => {
      this.direccion = direccion;
    });
  }

  previousState() {
    window.history.back();
  }
}
