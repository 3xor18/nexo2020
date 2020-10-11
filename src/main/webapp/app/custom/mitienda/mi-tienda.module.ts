import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MiTiendaRoutingModule } from './mitienda-routing.module';

/* Componentes */
import { IndexComponent } from './index/index.component';

@NgModule({
  declarations: [IndexComponent],
  exports: [IndexComponent],
  imports: [CommonModule, MiTiendaRoutingModule]
})
export class MiTiendaModule {}
