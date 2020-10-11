import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { PersonaDetailComponent } from 'app/entities/persona/persona-detail.component';
import { Persona } from 'app/shared/model/persona.model';

describe('Component Tests', () => {
  describe('Persona Management Detail Component', () => {
    let comp: PersonaDetailComponent;
    let fixture: ComponentFixture<PersonaDetailComponent>;
    const route = ({ data: of({ persona: new Persona(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [PersonaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.persona).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
