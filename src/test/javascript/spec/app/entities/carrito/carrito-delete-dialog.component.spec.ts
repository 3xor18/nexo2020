import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NexoTestModule } from '../../../test.module';
import { CarritoDeleteDialogComponent } from 'app/entities/carrito/carrito-delete-dialog.component';
import { CarritoService } from 'app/entities/carrito/carrito.service';

describe('Component Tests', () => {
  describe('Carrito Management Delete Component', () => {
    let comp: CarritoDeleteDialogComponent;
    let fixture: ComponentFixture<CarritoDeleteDialogComponent>;
    let service: CarritoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CarritoDeleteDialogComponent]
      })
        .overrideTemplate(CarritoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoService);
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
