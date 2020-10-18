import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductoService } from '../../../../entities/producto/producto.service';
import { IProducto } from '../../../../shared/model/producto.model';
import { AccountService } from '../../../../core/auth/account.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { UtilsService } from '../../../utils/utils.service';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { ModalAgregarProductoComponent } from '../modal-agregar/modal-agregar-producto.component';

@Component({
  selector: 'jhi-listado-productos',
  templateUrl: './listado-productos.component.html',
  styles: []
})
export class ListadoProductosComponent implements OnInit {
  @ViewChild(ModalAgregarProductoComponent, { static: false }) modalAgregar: ModalAgregarProductoComponent;

  currentAccount: any;
  eventSubscriber: Subscription;
  productos: IProducto[] = [];
  /* Paginador */
  paginador: any;
  paginas: number[];
  pag = 0;
  desde: number;
  hasta: number;
  estado2: string;

  constructor(
    protected utilsService: UtilsService,
    protected productoService: ProductoService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    this.paginar(0);
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.productoService.notificarCambio.subscribe(prod => this.productos.push(prod));
  }

  trackId(index: number, item: IProducto) {
    return item.id;
  }

  /* Paginador de productos*/
  paginar(pagina) {
    this.utilsService.popupLoading('Consultando...');
    this.productoService.getMyProducts(pagina).subscribe(res => {
      Swal.close();
      this.productos = res.content as IProducto[];
      this.paginador = res;
      this.desde = Math.min(Math.max(1, this.paginador.number - 4), this.paginador.totalPages - 5);
      this.hasta = Math.max(Math.min(this.paginador.totalPages, this.paginador.number + 4), 6);
      if (this.paginador.totalPages > 5) {
        this.paginas = new Array(this.hasta - this.desde + 1).fill(0).map((valor, indice) => indice + this.desde);
      } else {
        this.paginas = new Array(res.totalPages).fill(0).map((valor, indice) => indice + 1);
      }
    });
  }

  /* Abre el modal de crear o editar producto */
  abrirModalCrearProducto() {
    this.modalAgregar.toggle();
  }
}
