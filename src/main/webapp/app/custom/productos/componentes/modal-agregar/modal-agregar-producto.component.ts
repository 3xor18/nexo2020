import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-modal-agregar-producto',
  templateUrl: './modal-agregar-producto.component.html',
  styles: []
})
export class ModalAgregarProductoComponent implements OnInit {
  show: boolean;

  constructor() {}

  ngOnInit() {}

  public toggle() {
    this.show = !this.show;
  }
}
