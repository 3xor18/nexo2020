import { Component, OnInit } from '@angular/core';
import { UtilsService } from '../../utils/utils.service';

@Component({
  selector: 'jhi-index',
  templateUrl: './index.component.html',
  styles: []
})
export class IndexComponent implements OnInit {
  tabInvetario = 'INVENTARIO';
  tabTransacciones = 'TRANSACCIONES';
  tabActiva = this.tabInvetario;

  constructor(protected utilsService: UtilsService) {}

  ngOnInit() {}

  cambiarTab(tabIn: string) {
    this.tabActiva = tabIn;
  }
}
