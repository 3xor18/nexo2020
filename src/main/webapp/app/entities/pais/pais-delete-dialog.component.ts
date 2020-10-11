import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';

@Component({
  selector: 'jhi-pais-delete-dialog',
  templateUrl: './pais-delete-dialog.component.html'
})
export class PaisDeleteDialogComponent {
  pais: IPais;

  constructor(protected paisService: PaisService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paisService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'paisListModification',
        content: 'Deleted an pais'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pais-delete-popup',
  template: ''
})
export class PaisDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pais }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PaisDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pais = pais;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pais', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pais', { outlets: { popup: null } }]);
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
