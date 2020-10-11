import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDireccion } from 'app/shared/model/direccion.model';
import { DireccionService } from './direccion.service';

@Component({
  selector: 'jhi-direccion-delete-dialog',
  templateUrl: './direccion-delete-dialog.component.html'
})
export class DireccionDeleteDialogComponent {
  direccion: IDireccion;

  constructor(protected direccionService: DireccionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.direccionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'direccionListModification',
        content: 'Deleted an direccion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-direccion-delete-popup',
  template: ''
})
export class DireccionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ direccion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DireccionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.direccion = direccion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/direccion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/direccion', { outlets: { popup: null } }]);
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
