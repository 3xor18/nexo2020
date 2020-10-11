import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { CarritoUpdateComponent } from 'app/entities/carrito/carrito-update.component';
import { CarritoService } from 'app/entities/carrito/carrito.service';
import { Carrito } from 'app/shared/model/carrito.model';

describe('Component Tests', () => {
  describe('Carrito Management Update Component', () => {
    let comp: CarritoUpdateComponent;
    let fixture: ComponentFixture<CarritoUpdateComponent>;
    let service: CarritoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CarritoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarritoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarritoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Carrito(123);
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
        const entity = new Carrito();
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
