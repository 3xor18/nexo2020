import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoImpuestos } from 'app/shared/model/producto-impuestos.model';
import { ProductoImpuestosService } from './producto-impuestos.service';

@Component({
  selector: 'jhi-producto-impuestos-delete-dialog',
  templateUrl: './producto-impuestos-delete-dialog.component.html'
})
export class ProductoImpuestosDeleteDialogComponent {
  productoImpuestos: IProductoImpuestos;

  constructor(
    protected productoImpuestosService: ProductoImpuestosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productoImpuestosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productoImpuestosListModification',
        content: 'Deleted an productoImpuestos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-producto-impuestos-delete-popup',
  template: ''
})
export class ProductoImpuestosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoImpuestos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductoImpuestosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productoImpuestos = productoImpuestos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/producto-impuestos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/producto-impuestos', { outlets: { popup: null } }]);
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
