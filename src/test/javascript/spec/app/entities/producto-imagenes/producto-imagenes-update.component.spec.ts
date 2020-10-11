import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { ProductoImagenesUpdateComponent } from 'app/entities/producto-imagenes/producto-imagenes-update.component';
import { ProductoImagenesService } from 'app/entities/producto-imagenes/producto-imagenes.service';
import { ProductoImagenes } from 'app/shared/model/producto-imagenes.model';

describe('Component Tests', () => {
  describe('ProductoImagenes Management Update Component', () => {
    let comp: ProductoImagenesUpdateComponent;
    let fixture: ComponentFixture<ProductoImagenesUpdateComponent>;
    let service: ProductoImagenesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ProductoImagenesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductoImagenesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoImagenesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoImagenesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductoImagenes(123);
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
        const entity = new ProductoImagenes();
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
