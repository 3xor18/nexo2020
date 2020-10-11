import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from './persona.service';

@Component({
  selector: 'jhi-persona-delete-dialog',
  templateUrl: './persona-delete-dialog.component.html'
})
export class PersonaDeleteDialogComponent {
  persona: IPersona;

  constructor(protected personaService: PersonaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'personaListModification',
        content: 'Deleted an persona'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-persona-delete-popup',
  template: ''
})
export class PersonaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ persona }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PersonaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.persona = persona;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/persona', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/persona', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
