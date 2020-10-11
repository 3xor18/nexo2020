import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { CarritoProductoUpdateComponent } from 'app/entities/carrito-producto/carrito-producto-update.component';
import { CarritoProductoService } from 'app/entities/carrito-producto/carrito-producto.service';
import { CarritoProducto } from 'app/shared/model/carrito-producto.model';

describe('Component Tests', () => {
  describe('CarritoProducto Management Update Component', () => {
    let comp: CarritoProductoUpdateComponent;
    let fixture: ComponentFixture<CarritoProductoUpdateComponent>;
    let service: CarritoProductoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CarritoProductoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarritoProductoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarritoProductoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoProductoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CarritoProducto(123);
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
        const entity = new CarritoProducto();
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
