import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { CostoDeliveryUpdateComponent } from 'app/entities/costo-delivery/costo-delivery-update.component';
import { CostoDeliveryService } from 'app/entities/costo-delivery/costo-delivery.service';
import { CostoDelivery } from 'app/shared/model/costo-delivery.model';

describe('Component Tests', () => {
  describe('CostoDelivery Management Update Component', () => {
    let comp: CostoDeliveryUpdateComponent;
    let fixture: ComponentFixture<CostoDeliveryUpdateComponent>;
    let service: CostoDeliveryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CostoDeliveryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CostoDeliveryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CostoDeliveryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CostoDeliveryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CostoDelivery(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CostoDelivery();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
