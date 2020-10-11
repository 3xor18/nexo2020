import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProducto } from 'app/shared/model/producto.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductoService } from './producto.service';

@Component({
  selector: 'jhi-producto',
  templateUrl: './producto.component.html'
})
export class ProductoComponent implements OnInit, OnDestroy {
  productos: IProducto[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productoService: ProductoService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productoService
      .query()
      .pipe(
        filter((res: HttpResponse<IProducto[]>) => res.ok),
        map((res: HttpResponse<IProducto[]>) => res.body)
      )
      .subscribe((res: IProducto[]) => {
        this.productos = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProducto) {
    return item.id;
  }

  registerChangeInProductos() {
    this.eventSubscriber = this.eventManager.subscribe('productoListModification', response => this.loadAll());
  }
}
