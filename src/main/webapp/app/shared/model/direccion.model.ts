export interface IDireccion {
  id?: number;
  direccion?: string;
  estado?: string;
  principal?: boolean;
  comunaId?: number;
  personaId?: number;
}

export class Direccion implements IDireccion {
  constructor(
    public id?: number,
    public direccion?: string,
    public estado?: string,
    public principal?: boolean,
    public comunaId?: number,
    public personaId?: number
  ) {
    this.principal = this.principal || false;
  }
}
