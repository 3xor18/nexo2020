import { Component, Input, OnInit } from '@angular/core';
import { IProducto } from '../../../../shared/model/producto.model';

@Component({
  selector: 'jhi-card-producto',
  templateUrl: './card-producto.component.html',
  styles: []
})
export class CardProductoComponent implements OnInit {
  @Input() producto: any;
  product: IProducto;

  constructor() {}

  ngOnInit() {
    this.product = this.producto;
  }
}
