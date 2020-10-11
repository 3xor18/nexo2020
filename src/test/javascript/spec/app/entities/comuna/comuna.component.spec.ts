import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NexoTestModule } from '../../../test.module';
import { ComunaComponent } from 'app/entities/comuna/comuna.component';
import { ComunaService } from 'app/entities/comuna/comuna.service';
import { Comuna } from 'app/shared/model/comuna.model';

describe('Component Tests', () => {
  describe('Comuna Management Component', () => {
    let comp: ComunaComponent;
    let fixture: ComponentFixture<ComunaComponent>;
    let service: ComunaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [ComunaComponent],
        providers: []
      })
        .overrideTemplate(ComunaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComunaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComunaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Comuna(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.comunas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
