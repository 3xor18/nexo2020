import { Component, Input, OnInit } from '@angular/core';
import { IPais } from '../../../../../shared/model/pais.model';
import { IUnidadMedida } from '../../../../../shared/model/unidad-medida.model';

@Component({
  selector: 'jhi-datos-basicos',
  templateUrl: './datos-basicos.component.html',
  styles: []
})
export class DatosBasicosComponent implements OnInit {
  @Input() paises: IPais[];
  @Input() unidadesMedidas: IUnidadMedida[];

  constructor() {}

  ngOnInit() {}
}
