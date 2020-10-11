import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoImagenes } from 'app/shared/model/producto-imagenes.model';
import { ProductoImagenesService } from './producto-imagenes.service';

@Component({
  selector: 'jhi-producto-imagenes-delete-dialog',
  templateUrl: './producto-imagenes-delete-dialog.component.html'
})
export class ProductoImagenesDeleteDialogComponent {
  productoImagenes: IProductoImagenes;

  constructor(
    protected productoImagenesService: ProductoImagenesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productoImagenesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productoImagenesListModification',
        content: 'Deleted an productoImagenes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-producto-imagenes-delete-popup',
  template: ''
})
export class ProductoImagenesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoImagenes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductoImagenesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productoImagenes = productoImagenes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/producto-imagenes', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/producto-imagenes', { outlets: { popup: null } }]);
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
