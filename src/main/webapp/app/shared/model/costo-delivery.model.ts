import { Moment } from 'moment';

export interface ICostoDelivery {
  id?: number;
  estado?: string;
  montoIndividual?: number;
  montoMayor?: number;
  cantidadMayor?: number;
  fechaBd?: Moment;
  productoId?: number;
  comunaId?: number;
}

export class CostoDelivery implements ICostoDelivery {
  constructor(
    public id?: number,
    public estado?: string,
    public montoIndividual?: number,
    public montoMayor?: number,
    public cantidadMayor?: number,
    public fechaBd?: Moment,
    public productoId?: number,
    public comunaId?: number
  ) {}
}
