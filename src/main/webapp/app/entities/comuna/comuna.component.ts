import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IComuna } from 'app/shared/model/comuna.model';
import { AccountService } from 'app/core/auth/account.service';
import { ComunaService } from './comuna.service';

@Component({
  selector: 'jhi-comuna',
  templateUrl: './comuna.component.html'
})
export class ComunaComponent implements OnInit, OnDestroy {
  comunas: IComuna[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected comunaService: ComunaService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.comunaService
      .query()
      .pipe(
        filter((res: HttpResponse<IComuna[]>) => res.ok),
        map((res: HttpResponse<IComuna[]>) => res.body)
      )
      .subscribe((res: IComuna[]) => {
        this.comunas = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInComunas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IComuna) {
    return item.id;
  }

  registerChangeInComunas() {
    this.eventSubscriber = this.eventManager.subscribe('comunaListModification', response => this.loadAll());
  }
}
