import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NexoTestModule } from '../../../test.module';
import { CostoDeliveryDeleteDialogComponent } from 'app/entities/costo-delivery/costo-delivery-delete-dialog.component';
import { CostoDeliveryService } from 'app/entities/costo-delivery/costo-delivery.service';

describe('Component Tests', () => {
  describe('CostoDelivery Management Delete Component', () => {
    let comp: CostoDeliveryDeleteDialogComponent;
    let fixture: ComponentFixture<CostoDeliveryDeleteDialogComponent>;
    let service: CostoDeliveryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CostoDeliveryDeleteDialogComponent]
      })
        .overrideTemplate(CostoDeliveryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CostoDeliveryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CostoDeliveryService);
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
