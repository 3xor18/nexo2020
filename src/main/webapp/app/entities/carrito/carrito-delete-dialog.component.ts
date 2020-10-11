import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarrito } from 'app/shared/model/carrito.model';
import { CarritoService } from './carrito.service';

@Component({
  selector: 'jhi-carrito-delete-dialog',
  templateUrl: './carrito-delete-dialog.component.html'
})
export class CarritoDeleteDialogComponent {
  carrito: ICarrito;

  constructor(protected carritoService: CarritoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.carritoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'carritoListModification',
        content: 'Deleted an carrito'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-carrito-delete-popup',
  template: ''
})
export class CarritoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carrito }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CarritoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.carrito = carrito;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/carrito', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/carrito', { outlets: { popup: null } }]);
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
