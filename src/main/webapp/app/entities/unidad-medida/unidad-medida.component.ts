import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { AccountService } from 'app/core/auth/account.service';
import { UnidadMedidaService } from './unidad-medida.service';

@Component({
  selector: 'jhi-unidad-medida',
  templateUrl: './unidad-medida.component.html'
})
export class UnidadMedidaComponent implements OnInit, OnDestroy {
  unidadMedidas: IUnidadMedida[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected unidadMedidaService: UnidadMedidaService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.unidadMedidaService
      .query()
      .pipe(
        filter((res: HttpResponse<IUnidadMedida[]>) => res.ok),
        map((res: HttpResponse<IUnidadMedida[]>) => res.body)
      )
      .subscribe((res: IUnidadMedida[]) => {
        this.unidadMedidas = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUnidadMedidas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUnidadMedida) {
    return item.id;
  }

  registerChangeInUnidadMedidas() {
    this.eventSubscriber = this.eventManager.subscribe('unidadMedidaListModification', response => this.loadAll());
  }
}
