import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MiCarritoRoutingModule } from './micarrito-router.module';
import { IndexComponent } from './index/index.component';

/* Componentes */

@NgModule({
  declarations: [IndexComponent],
  exports: [IndexComponent],
  imports: [CommonModule, MiCarritoRoutingModule]
})
export class MiCarritoModule {}
