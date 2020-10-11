import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarritoProducto } from 'app/shared/model/carrito-producto.model';
import { CarritoProductoService } from './carrito-producto.service';

@Component({
  selector: 'jhi-carrito-producto-delete-dialog',
  templateUrl: './carrito-producto-delete-dialog.component.html'
})
export class CarritoProductoDeleteDialogComponent {
  carritoProducto: ICarritoProducto;

  constructor(
    protected carritoProductoService: CarritoProductoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.carritoProductoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'carritoProductoListModification',
        content: 'Deleted an carritoProducto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-carrito-producto-delete-popup',
  template: ''
})
export class CarritoProductoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carritoProducto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CarritoProductoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.carritoProducto = carritoProducto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/carrito-producto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/carrito-producto', { outlets: { popup: null } }]);
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
