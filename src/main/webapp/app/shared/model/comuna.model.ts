export interface IComuna {
  id?: number;
  nombre?: string;
  regionId?: number;
}

export class Comuna implements IComuna {
  constructor(public id?: number, public nombre?: string, public regionId?: number) {}
}
