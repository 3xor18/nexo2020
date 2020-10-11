import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProductoImpuestosService } from 'app/entities/producto-impuestos/producto-impuestos.service';
import { IProductoImpuestos, ProductoImpuestos } from 'app/shared/model/producto-impuestos.model';

describe('Service Tests', () => {
  describe('ProductoImpuestos Service', () => {
    let injector: TestBed;
    let service: ProductoImpuestosService;
    let httpMock: HttpTestingController;
    let elemDefault: IProductoImpuestos;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProductoImpuestosService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ProductoImpuestos(0, 'AAAAAAA', 'AAAAAAA', currentDate, false, 0, false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaBd: currentDate.format(DATE_TIME_FORMAT),
            fechaAplicable: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a ProductoImpuestos', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaBd: currentDate.format(DATE_TIME_FORMAT),
            fechaAplicable: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaBd: currentDate,
            fechaAplicable: currentDate
          },
          returnedFromService
        );
        service
          .create(new ProductoImpuestos(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ProductoImpuestos', () => {
        const returnedFromService = Object.assign(
          {
            estado: 'BBBBBB',
            nombre: 'BBBBBB',
            fechaBd: currentDate.format(DATE_TIME_FORMAT),
            impuestoMontoFijo: true,
            montoOTasa: 1,
            impuestoNacional: true,
            fechaAplicable: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaBd: currentDate,
            fechaAplicable: currentDate
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

      it('should return a list of ProductoImpuestos', () => {
        const returnedFromService = Object.assign(
          {
            estado: 'BBBBBB',
            nombre: 'BBBBBB',
            fechaBd: currentDate.format(DATE_TIME_FORMAT),
            impuestoMontoFijo: true,
            montoOTasa: 1,
            impuestoNacional: true,
            fechaAplicable: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaBd: currentDate,
            fechaAplicable: currentDate
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

      it('should delete a ProductoImpuestos', () => {
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
