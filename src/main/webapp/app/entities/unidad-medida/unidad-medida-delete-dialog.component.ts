import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from './unidad-medida.service';

@Component({
  selector: 'jhi-unidad-medida-delete-dialog',
  templateUrl: './unidad-medida-delete-dialog.component.html'
})
export class UnidadMedidaDeleteDialogComponent {
  unidadMedida: IUnidadMedida;

  constructor(
    protected unidadMedidaService: UnidadMedidaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.unidadMedidaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'unidadMedidaListModification',
        content: 'Deleted an unidadMedida'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-unidad-medida-delete-popup',
  template: ''
})
export class UnidadMedidaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ unidadMedida }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UnidadMedidaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.unidadMedida = unidadMedida;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/unidad-medida', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/unidad-medida', { outlets: { popup: null } }]);
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
