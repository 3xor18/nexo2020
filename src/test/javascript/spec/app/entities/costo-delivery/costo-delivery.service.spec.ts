import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CostoDeliveryService } from 'app/entities/costo-delivery/costo-delivery.service';
import { ICostoDelivery, CostoDelivery } from 'app/shared/model/costo-delivery.model';

describe('Service Tests', () => {
  describe('CostoDelivery Service', () => {
    let injector: TestBed;
    let service: CostoDeliveryService;
    let httpMock: HttpTestingController;
    let elemDefault: ICostoDelivery;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CostoDeliveryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CostoDelivery(0, 'AAAAAAA', 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaBd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a CostoDelivery', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaBd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaBd: currentDate
          },
          returnedFromService
        );
        service
          .create(new CostoDelivery(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CostoDelivery', () => {
        const returnedFromService = Object.assign(
          {
            estado: 'BBBBBB',
            montoIndividual: 1,
            montoMayor: 1,
            cantidadMayor: 1,
            fechaBd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaBd: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of CostoDelivery', () => {
        const returnedFromService = Object.assign(
          {
            estado: 'BBBBBB',
            montoIndividual: 1,
            montoMayor: 1,
            cantidadMayor: 1,
            fechaBd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaBd: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CostoDelivery', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
