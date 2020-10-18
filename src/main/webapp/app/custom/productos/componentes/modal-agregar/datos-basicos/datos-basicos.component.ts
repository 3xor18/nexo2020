import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IPais } from '../../../../../shared/model/pais.model';
import { IUnidadMedida } from '../../../../../shared/model/unidad-medida.model';
import { FormBuilder, Validators } from '@angular/forms';
import { IProducto, Producto } from '../../../../../shared/model/producto.model';
import * as constants from '../../../../../app.constants';
import { FileManagerService } from '../../../../utils/fileManager.service';
import { ProductoService } from '../../../../../entities/producto/producto.service';
import { ProductoImagenesService } from '../../../../../entities/producto-imagenes/producto-imagenes.service';
import { IProductoImagenes, ProductoImagenes } from '../../../../../shared/model/producto-imagenes.model';
import { UtilsService } from '../../../../utils/utils.service';

@Component({
  selector: 'jhi-datos-basicos',
  templateUrl: './datos-basicos.component.html',
  styles: []
})
export class DatosBasicosComponent implements OnInit {
  @Input() paises: IPais[];
  @Input() unidadesMedidas: IUnidadMedida[];
  imagen: File;
  tipo = constants.IMAGEN_PRODUCTO;
  path = '';

  @Output() respuesta = new EventEmitter<string>();

  editForm = this.fb.group({
    nombre: ['', [Validators.required]],
    codigoInterno: [],
    codigoBarra: [],
    descripcion: ['', [Validators.required]],
    unidadMedida: [1, [Validators.required]],
    unidadVender: [1, [Validators.required]],
    pais: [1, [Validators.required]],
    cantidad: [1, [Validators.required]],
    precioCompra: ['', [Validators.required]],
    precioDetal: ['', [Validators.required]],
    precioMayor: [],
    mayorDespues: [],
    imagen: ['', [Validators.required]],
    alerta: [1, [Validators.required]]
  });

  constructor(
    private fb: FormBuilder,
    protected fileService: FileManagerService,
    protected productoService: ProductoService,
    protected fotoProductoService: ProductoImagenesService,
    protected utilsService: UtilsService
  ) {}

  ngOnInit() {}

  /* guarda el producto */
  saveProducto() {
    this.utilsService.popupLoading('Creando producto');
    const producto = this.crearObjetoProducto();
    this.productoService.crearProducto(producto).subscribe(res => {
      this.productoService.notificarCambio.emit(res);
      this.subirFoto(res.id);
    });
  }

  /* sube la foto al servidor */
  subirFoto(productoId: number) {
    this.fileService.uploadFile(this.imagen, this.tipo).subscribe(res => {
      this.path = res;
      this.agregarFotoAlProducto(productoId);
    });
  }

  /* guarda la foto con el producto */
  agregarFotoAlProducto(idProducto: number) {
    const entity = this.crearobjetoProductoFoto(idProducto);
    this.fotoProductoService.agreagarImagenAProducto(entity).subscribe(res => {
      this.utilsService.popupInfo('Producto Creado').then(async x => {
        const resp = await this.utilsService.popupPregunta('¿Desea agregar cargos por delivery?');
        this.emitirRespuesta(resp);
      });
    });
  }

  /* emite la respuesta de si desea agregar cargos por delivery */
  emitirRespuesta(res: string) {
    this.respuesta.emit(res);
  }

  /* Crea el objeto q contiene el producto y la foto */
  crearobjetoProductoFoto(productoIdIn: number): IProductoImagenes {
    return {
      ...new ProductoImagenes(),
      path: this.path,
      productoId: productoIdIn
    };
  }

  /* Crea el producto para guardar */
  crearObjetoProducto(): IProducto {
    return {
      ...new Producto(),
      alertaMinimo: this.editForm.get(['alerta']).value,
      cantidadDisponible: this.editForm.get(['cantidad']).value,
      codigo: this.editForm.get(['codigoInterno']).value,
      codigoBarra: this.editForm.get(['codigoBarra']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      nombre: this.editForm.get(['nombre']).value,
      precioAlmayorDespuesde: this.editForm.get(['mayorDespues']).value,
      precioCompraBruto: this.editForm.get(['precioCompra']).value,
      precioVentaTotalDetal: this.editForm.get(['precioDetal']).value,
      precioVentaTotalMayor: this.editForm.get(['precioMayor']).value,
      unidadMedidaVendida: this.editForm.get(['unidadVender']).value,
      unidadMedidaId: this.editForm.get(['unidadMedida']).value,
      elaboradoEnId: this.editForm.get(['pais']).value
    };
  }

  private comprobarString(valor: string) {}

  private comprobarNumeros(numero: number) {}

  /* Carga el producto en el formulario */
  cargarFormulario(producto: IProducto) {
    this.editForm.patchValue({
      nombre: producto.nombre,
      codigoInterno: producto.codigo,
      codigoBarra: producto.codigoBarra,
      descripcion: producto.descripcion,
      unidadMedida: producto.unidadMedidaId,
      unidadVender: producto.unidadMedidaVendida,
      pais: producto.elaboradoEnId,
      cantidad: producto.cantidadDisponible,
      precioCompra: producto.precioCompraBruto,
      precioDetal: producto.precioVentaTotalDetal,
      precioMayor: producto.precioVentaTotalMayor,
      mayorDespues: producto.precioAlmayorDespuesde,
      alerta: producto.alertaMinimo
    });
  }

  // Setea archivo seleccionado por el usuario desde modal
  selectArchivo(event) {
    this.imagen = event.target.files[0];
  }
}
