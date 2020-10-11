import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICostoDelivery } from 'app/shared/model/costo-delivery.model';
import { CostoDeliveryService } from './costo-delivery.service';

@Component({
  selector: 'jhi-costo-delivery-delete-dialog',
  templateUrl: './costo-delivery-delete-dialog.component.html'
})
export class CostoDeliveryDeleteDialogComponent {
  costoDelivery: ICostoDelivery;

  constructor(
    protected costoDeliveryService: CostoDeliveryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.costoDeliveryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'costoDeliveryListModification',
        content: 'Deleted an costoDelivery'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-costo-delivery-delete-popup',
  template: ''
})
export class CostoDeliveryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ costoDelivery }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CostoDeliveryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.costoDelivery = costoDelivery;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/costo-delivery', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/costo-delivery', { outlets: { popup: null } }]);
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
