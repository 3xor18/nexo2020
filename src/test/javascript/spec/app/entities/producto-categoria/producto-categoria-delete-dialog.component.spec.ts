import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NexoTestModule } from '../../../test.module';
import { ProductoCategoriaDeleteDialogComponent } from 'app/entities/producto-categoria/producto-categoria-delete-dialog.component';
import { ProductoCategoriaService } from 'app/entities/producto-categoria/producto-categoria.service';

describe('Component Tests', () => {
  describe('ProductoCategoria Management Delete Component', () => {
    let comp: ProductoCategoriaDeleteDialogComponent;
    let fixture: ComponentFixture<ProductoCategoriaDeleteDialogComponent>;
    let service: ProductoCategoriaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoCategoriaDeleteDialogComponent]
      })
        .overrideTemplate(ProductoCategoriaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductoCategoriaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoCategoriaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
