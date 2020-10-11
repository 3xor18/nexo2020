import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICostoDelivery } from 'app/shared/model/costo-delivery.model';

@Component({
  selector: 'jhi-costo-delivery-detail',
  templateUrl: './costo-delivery-detail.component.html'
})
export class CostoDeliveryDetailComponent implements OnInit {
  costoDelivery: ICostoDelivery;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ costoDelivery }) => {
      this.costoDelivery = costoDelivery;
    });
  }

  previousState() {
    window.history.back();
  }
}
