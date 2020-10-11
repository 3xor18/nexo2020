import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { DireccionUpdateComponent } from 'app/entities/direccion/direccion-update.component';
import { DireccionService } from 'app/entities/direccion/direccion.service';
import { Direccion } from 'app/shared/model/direccion.model';

describe('Component Tests', () => {
  describe('Direccion Management Update Component', () => {
    let comp: DireccionUpdateComponent;
    let fixture: ComponentFixture<DireccionUpdateComponent>;
    let service: DireccionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [DireccionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DireccionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DireccionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DireccionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Direccion(123);
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
        const entity = new Direccion();
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
