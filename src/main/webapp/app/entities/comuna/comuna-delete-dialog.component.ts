import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from './comuna.service';

@Component({
  selector: 'jhi-comuna-delete-dialog',
  templateUrl: './comuna-delete-dialog.component.html'
})
export class ComunaDeleteDialogComponent {
  comuna: IComuna;

  constructor(protected comunaService: ComunaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.comunaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'comunaListModification',
        content: 'Deleted an comuna'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-comuna-delete-popup',
  template: ''
})
export class ComunaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comuna }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ComunaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.comuna = comuna;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/comuna', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/comuna', { outlets: { popup: null } }]);
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
