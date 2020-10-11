import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { UnidadMedidaUpdateComponent } from 'app/entities/unidad-medida/unidad-medida-update.component';
import { UnidadMedidaService } from 'app/entities/unidad-medida/unidad-medida.service';
import { UnidadMedida } from 'app/shared/model/unidad-medida.model';

describe('Component Tests', () => {
  describe('UnidadMedida Management Update Component', () => {
    let comp: UnidadMedidaUpdateComponent;
    let fixture: ComponentFixture<UnidadMedidaUpdateComponent>;
    let service: UnidadMedidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [UnidadMedidaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UnidadMedidaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UnidadMedidaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UnidadMedidaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UnidadMedida(123);
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
        const entity = new UnidadMedida();
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
