import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NexoTestModule } from '../../../test.module';
import { PersonaComponent } from 'app/entities/persona/persona.component';
import { PersonaService } from 'app/entities/persona/persona.service';
import { Persona } from 'app/shared/model/persona.model';

describe('Component Tests', () => {
  describe('Persona Management Component', () => {
    let comp: PersonaComponent;
    let fixture: ComponentFixture<PersonaComponent>;
    let service: PersonaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [PersonaComponent],
        providers: []
      })
        .overrideTemplate(PersonaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Persona(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
