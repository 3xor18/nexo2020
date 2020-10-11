import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { PersonaUpdateComponent } from 'app/entities/persona/persona-update.component';
import { PersonaService } from 'app/entities/persona/persona.service';
import { Persona } from 'app/shared/model/persona.model';

describe('Component Tests', () => {
  describe('Persona Management Update Component', () => {
    let comp: PersonaUpdateComponent;
    let fixture: ComponentFixture<PersonaUpdateComponent>;
    let service: PersonaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [PersonaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Persona(123);
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
        const entity = new Persona();
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
