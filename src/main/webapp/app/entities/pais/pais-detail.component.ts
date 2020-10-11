import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPais } from 'app/shared/model/pais.model';

@Component({
  selector: 'jhi-pais-detail',
  templateUrl: './pais-detail.component.html'
})
export class PaisDetailComponent implements OnInit {
  pais: IPais;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pais }) => {
      this.pais = pais;
    });
  }

  previousState() {
    window.history.back();
  }
}
