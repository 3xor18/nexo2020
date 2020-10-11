export interface IRegion {
  id?: number;
  nombre?: string;
  paisId?: number;
}

export class Region implements IRegion {
  constructor(public id?: number, public nombre?: string, public paisId?: number) {}
}
