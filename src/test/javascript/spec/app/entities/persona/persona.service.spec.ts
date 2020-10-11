import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PersonaService } from 'app/entities/persona/persona.service';
import { IPersona, Persona } from 'app/shared/model/persona.model';

describe('Service Tests', () => {
  describe('Persona Service', () => {
    let injector: TestBed;
    let service: PersonaService;
    let httpMock: HttpTestingController;
    let elemDefault: IPersona;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PersonaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Persona(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaNac: currentDate.format(DATE_FORMAT)
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

      it('should create a Persona', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaNac: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaNac: currentDate
          },
          returnedFromService
        );
        service
          .create(new Persona(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Persona', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            apellidoPaterno: 'BBBBBB',
            apellidoMaterno: 'BBBBBB',
            docIdentidad: 'BBBBBB',
            fechaNac: currentDate.format(DATE_FORMAT),
            sexo: 'BBBBBB',
            nacionalidad: 'BBBBBB',
            email: 'BBBBBB',
            telefono: 'BBBBBB',
            estado: 'BBBBBB',
            natural: true,
            nombreComercial: 'BBBBBB',
            scoreComprador: 1,
            scoreVendedor: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaNac: currentDate
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

      it('should return a list of Persona', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            apellidoPaterno: 'BBBBBB',
            apellidoMaterno: 'BBBBBB',
            docIdentidad: 'BBBBBB',
            fechaNac: currentDate.format(DATE_FORMAT),
            sexo: 'BBBBBB',
            nacionalidad: 'BBBBBB',
            email: 'BBBBBB',
            telefono: 'BBBBBB',
            estado: 'BBBBBB',
            natural: true,
            nombreComercial: 'BBBBBB',
            scoreComprador: 1,
            scoreVendedor: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaNac: currentDate
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

      it('should delete a Persona', () => {
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
