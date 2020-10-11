import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoImagenes } from 'app/shared/model/producto-imagenes.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductoImagenesService } from './producto-imagenes.service';

@Component({
  selector: 'jhi-producto-imagenes',
  templateUrl: './producto-imagenes.component.html'
})
export class ProductoImagenesComponent implements OnInit, OnDestroy {
  productoImagenes: IProductoImagenes[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productoImagenesService: ProductoImagenesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productoImagenesService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductoImagenes[]>) => res.ok),
        map((res: HttpResponse<IProductoImagenes[]>) => res.body)
      )
      .subscribe((res: IProductoImagenes[]) => {
        this.productoImagenes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductoImagenes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductoImagenes) {
    return item.id;
  }

  registerChangeInProductoImagenes() {
    this.eventSubscriber = this.eventManager.subscribe('productoImagenesListModification', response => this.loadAll());
  }
}
