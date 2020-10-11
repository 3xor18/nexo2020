import { Moment } from 'moment';

export interface IUnidadMedida {
  id?: number;
  estado?: string;
  nombre?: string;
  fechaBd?: Moment;
}

export class UnidadMedida implements IUnidadMedida {
  constructor(public id?: number, public estado?: string, public nombre?: string, public fechaBd?: Moment) {}
}
