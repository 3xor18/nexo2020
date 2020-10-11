import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoCategoria } from 'app/shared/model/producto-categoria.model';
import { ProductoCategoriaService } from './producto-categoria.service';

@Component({
  selector: 'jhi-producto-categoria-delete-dialog',
  templateUrl: './producto-categoria-delete-dialog.component.html'
})
export class ProductoCategoriaDeleteDialogComponent {
  productoCategoria: IProductoCategoria;

  constructor(
    protected productoCategoriaService: ProductoCategoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productoCategoriaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productoCategoriaListModification',
        content: 'Deleted an productoCategoria'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-producto-categoria-delete-popup',
  template: ''
})
export class ProductoCategoriaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoCategoria }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductoCategoriaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productoCategoria = productoCategoria;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/producto-categoria', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/producto-categoria', { outlets: { popup: null } }]);
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
