import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'persona',
        loadChildren: () => import('./persona/persona.module').then(m => m.NexoPersonaModule)
      },
      {
        path: 'direccion',
        loadChildren: () => import('./direccion/direccion.module').then(m => m.NexoDireccionModule)
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.NexoCategoriaModule)
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.NexoProductoModule)
      },
      {
        path: 'producto-categoria',
        loadChildren: () => import('./producto-categoria/producto-categoria.module').then(m => m.NexoProductoCategoriaModule)
      },
      {
        path: 'costo-delivery',
        loadChildren: () => import('./costo-delivery/costo-delivery.module').then(m => m.NexoCostoDeliveryModule)
      },
      {
        path: 'pais',
        loadChildren: () => import('./pais/pais.module').then(m => m.NexoPaisModule)
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.NexoRegionModule)
      },
      {
        path: 'comuna',
        loadChildren: () => import('./comuna/comuna.module').then(m => m.NexoComunaModule)
      },
      {
        path: 'carrito',
        loadChildren: () => import('./carrito/carrito.module').then(m => m.NexoCarritoModule)
      },
      {
        path: 'carrito-producto',
        loadChildren: () => import('./carrito-producto/carrito-producto.module').then(m => m.NexoCarritoProductoModule)
      },
      {
        path: 'unidad-medida',
        loadChildren: () => import('./unidad-medida/unidad-medida.module').then(m => m.NexoUnidadMedidaModule)
      },
      {
        path: 'producto-impuestos',
        loadChildren: () => import('./producto-impuestos/producto-impuestos.module').then(m => m.NexoProductoImpuestosModule)
      },
      {
        path: 'producto-imagenes',
        loadChildren: () => import('./producto-imagenes/producto-imagenes.module').then(m => m.NexoProductoImagenesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class NexoEntityModule {}
