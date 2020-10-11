import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { ProductoImpuestosUpdateComponent } from 'app/entities/producto-impuestos/producto-impuestos-update.component';
import { ProductoImpuestosService } from 'app/entities/producto-impuestos/producto-impuestos.service';
import { ProductoImpuestos } from 'app/shared/model/producto-impuestos.model';

describe('Component Tests', () => {
  describe('ProductoImpuestos Management Update Component', () => {
    let comp: ProductoImpuestosUpdateComponent;
    let fixture: ComponentFixture<ProductoImpuestosUpdateComponent>;
    let service: ProductoImpuestosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoImpuestosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductoImpuestosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoImpuestosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoImpuestosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductoImpuestos(123);
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
        const entity = new ProductoImpuestos();
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
