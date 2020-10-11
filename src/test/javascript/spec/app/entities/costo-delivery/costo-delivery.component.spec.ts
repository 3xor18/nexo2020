import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NexoTestModule } from '../../../test.module';
import { CostoDeliveryComponent } from 'app/entities/costo-delivery/costo-delivery.component';
import { CostoDeliveryService } from 'app/entities/costo-delivery/costo-delivery.service';
import { CostoDelivery } from 'app/shared/model/costo-delivery.model';

describe('Component Tests', () => {
  describe('CostoDelivery Management Component', () => {
    let comp: CostoDeliveryComponent;
    let fixture: ComponentFixture<CostoDeliveryComponent>;
    let service: CostoDeliveryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CostoDeliveryComponent],
        providers: []
      })
        .overrideTemplate(CostoDeliveryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CostoDeliveryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CostoDeliveryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CostoDelivery(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.costoDeliveries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
