import { Moment } from 'moment';

export interface IPersona {
  id?: number;
  nombre?: string;
  apellidoPaterno?: string;
  apellidoMaterno?: string;
  docIdentidad?: string;
  fechaNac?: Moment;
  sexo?: string;
  nacionalidad?: string;
  email?: string;
  telefono?: string;
  estado?: string;
  natural?: boolean;
  nombreComercial?: string;
  scoreComprador?: number;
  scoreVendedor?: number;
  paisId?: number;
  userLogin?: string;
  userId?: number;
}

export class Persona implements IPersona {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellidoPaterno?: string,
    public apellidoMaterno?: string,
    public docIdentidad?: string,
    public fechaNac?: Moment,
    public sexo?: string,
    public nacionalidad?: string,
    public email?: string,
    public telefono?: string,
    public estado?: string,
    public natural?: boolean,
    public nombreComercial?: string,
    public scoreComprador?: number,
    public scoreVendedor?: number,
    public paisId?: number,
    public userLogin?: string,
    public userId?: number
  ) {
    this.natural = this.natural || false;
  }
}
