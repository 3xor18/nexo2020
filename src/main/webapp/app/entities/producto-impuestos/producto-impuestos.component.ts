import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoImpuestos } from 'app/shared/model/producto-impuestos.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductoImpuestosService } from './producto-impuestos.service';

@Component({
  selector: 'jhi-producto-impuestos',
  templateUrl: './producto-impuestos.component.html'
})
export class ProductoImpuestosComponent implements OnInit, OnDestroy {
  productoImpuestos: IProductoImpuestos[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productoImpuestosService: ProductoImpuestosService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productoImpuestosService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductoImpuestos[]>) => res.ok),
        map((res: HttpResponse<IProductoImpuestos[]>) => res.body)
      )
      .subscribe((res: IProductoImpuestos[]) => {
        this.productoImpuestos = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductoImpuestos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductoImpuestos) {
    return item.id;
  }

  registerChangeInProductoImpuestos() {
    this.eventSubscriber = this.eventManager.subscribe('productoImpuestosListModification', response => this.loadAll());
  }
}
