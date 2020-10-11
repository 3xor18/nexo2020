import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../../../../entities/producto/producto.service';
import { UtilsService } from '../../../utils/utils.service';

@Component({
  selector: 'jhi-listado-productos',
  templateUrl: './listado-productos.component.html',
  styles: []
})
export class ListadoProductosComponent implements OnInit {
  constructor(protected productosService: ProductoService, protected utilsService: UtilsService) {}

  ngOnInit() {}
}
