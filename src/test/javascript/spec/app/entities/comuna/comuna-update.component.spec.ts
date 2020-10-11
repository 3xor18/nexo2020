import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { ComunaUpdateComponent } from 'app/entities/comuna/comuna-update.component';
import { ComunaService } from 'app/entities/comuna/comuna.service';
import { Comuna } from 'app/shared/model/comuna.model';

describe('Component Tests', () => {
  describe('Comuna Management Update Component', () => {
    let comp: ComunaUpdateComponent;
    let fixture: ComponentFixture<ComunaUpdateComponent>;
    let service: ComunaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ComunaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComunaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComunaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComunaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Comuna(123);
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
        const entity = new Comuna();
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
