import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NexoTestModule } from '../../../test.module';
import { CarritoProductoDeleteDialogComponent } from 'app/entities/carrito-producto/carrito-producto-delete-dialog.component';
import { CarritoProductoService } from 'app/entities/carrito-producto/carrito-producto.service';

describe('Component Tests', () => {
  describe('CarritoProducto Management Delete Component', () => {
    let comp: CarritoProductoDeleteDialogComponent;
    let fixture: ComponentFixture<CarritoProductoDeleteDialogComponent>;
    let service: CarritoProductoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CarritoProductoDeleteDialogComponent]
      })
        .overrideTemplate(CarritoProductoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoProductoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoProductoService);
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
