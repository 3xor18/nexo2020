export interface ICategoria {
  id?: number;
  nombre?: string;
  estado?: string;
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombre?: string, public estado?: string) {}
}
