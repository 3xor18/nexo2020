import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NexoTestModule } from '../../../test.module';
import { ComunaDeleteDialogComponent } from 'app/entities/comuna/comuna-delete-dialog.component';
import { ComunaService } from 'app/entities/comuna/comuna.service';

describe('Component Tests', () => {
  describe('Comuna Management Delete Component', () => {
    let comp: ComunaDeleteDialogComponent;
    let fixture: ComponentFixture<ComunaDeleteDialogComponent>;
    let service: ComunaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ComunaDeleteDialogComponent]
      })
        .overrideTemplate(ComunaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComunaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComunaService);
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
