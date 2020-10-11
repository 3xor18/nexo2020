import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NexoTestModule } from '../../../test.module';
import { CostoDeliveryDetailComponent } from 'app/entities/costo-delivery/costo-delivery-detail.component';
import { CostoDelivery } from 'app/shared/model/costo-delivery.model';

describe('Component Tests', () => {
  describe('CostoDelivery Management Detail Component', () => {
    let comp: CostoDeliveryDetailComponent;
    let fixture: ComponentFixture<CostoDeliveryDetailComponent>;
    const route = ({ data: of({ costoDelivery: new CostoDelivery(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NexoTestModule],
        declarations: [CostoDeliveryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CostoDeliveryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CostoDeliveryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.costoDelivery).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
