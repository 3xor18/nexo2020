import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoCategoria } from 'app/shared/model/producto-categoria.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductoCategoriaService } from './producto-categoria.service';

@Component({
  selector: 'jhi-producto-categoria',
  templateUrl: './producto-categoria.component.html'
})
export class ProductoCategoriaComponent implements OnInit, OnDestroy {
  productoCategorias: IProductoCategoria[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productoCategoriaService: ProductoCategoriaService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productoCategoriaService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductoCategoria[]>) => res.ok),
        map((res: HttpResponse<IProductoCategoria[]>) => res.body)
      )
      .subscribe((res: IProductoCategoria[]) => {
        this.productoCategorias = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductoCategorias();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductoCategoria) {
    return item.id;
  }

  registerChangeInProductoCategorias() {
    this.eventSubscriber = this.eventManager.subscribe('productoCategoriaListModification', response => this.loadAll());
  }
}
