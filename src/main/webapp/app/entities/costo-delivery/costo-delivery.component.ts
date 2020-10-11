import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICostoDelivery } from 'app/shared/model/costo-delivery.model';
import { AccountService } from 'app/core/auth/account.service';
import { CostoDeliveryService } from './costo-delivery.service';

@Component({
  selector: 'jhi-costo-delivery',
  templateUrl: './costo-delivery.component.html'
})
export class CostoDeliveryComponent implements OnInit, OnDestroy {
  costoDeliveries: ICostoDelivery[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected costoDeliveryService: CostoDeliveryService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.costoDeliveryService
      .query()
      .pipe(
        filter((res: HttpResponse<ICostoDelivery[]>) => res.ok),
        map((res: HttpResponse<ICostoDelivery[]>) => res.body)
      )
      .subscribe((res: ICostoDelivery[]) => {
        this.costoDeliveries = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCostoDeliveries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICostoDelivery) {
    return item.id;
  }

  registerChangeInCostoDeliveries() {
    this.eventSubscriber = this.eventManager.subscribe('costoDeliveryListModification', response => this.loadAll());
  }
}
