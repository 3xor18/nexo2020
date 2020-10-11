import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComuna } from 'app/shared/model/comuna.model';

@Component({
  selector: 'jhi-comuna-detail',
  templateUrl: './comuna-detail.component.html'
})
export class ComunaDetailComponent implements OnInit {
  comuna: IComuna;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comuna }) => {
      this.comuna = comuna;
    });
  }

  previousState() {
    window.history.back();
  }
}
