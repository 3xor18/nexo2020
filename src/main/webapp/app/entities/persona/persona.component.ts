import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPersona } from 'app/shared/model/persona.model';
import { AccountService } from 'app/core/auth/account.service';
import { PersonaService } from './persona.service';

@Component({
  selector: 'jhi-persona',
  templateUrl: './persona.component.html'
})
export class PersonaComponent implements OnInit, OnDestroy {
  personas: IPersona[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected personaService: PersonaService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personaService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersona[]>) => res.ok),
        map((res: HttpResponse<IPersona[]>) => res.body)
      )
      .subscribe((res: IPersona[]) => {
        this.personas = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPersonas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersona) {
    return item.id;
  }

  registerChangeInPersonas() {
    this.eventSubscriber = this.eventManager.subscribe('personaListModification', response => this.loadAll());
  }
}
