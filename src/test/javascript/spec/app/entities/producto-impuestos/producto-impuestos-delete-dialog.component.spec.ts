import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NexoTestModule } from '../../../test.module';
import { ProductoImpuestosDeleteDialogComponent } from 'app/entities/producto-impuestos/producto-impuestos-delete-dialog.component';
import { ProductoImpuestosService } from 'app/entities/producto-impuestos/producto-impuestos.service';

describe('Component Tests', () => {
  describe('ProductoImpuestos Management Delete Component', () => {
    let comp: ProductoImpuestosDeleteDialogComponent;
    let fixture: ComponentFixture<ProductoImpuestosDeleteDialogComponent>;
    let service: ProductoImpuestosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoImpuestosDeleteDialogComponent]
      })
        .overrideTemplate(ProductoImpuestosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductoImpuestosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoImpuestosService);
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
