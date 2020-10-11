import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NexoSharedModule } from 'app/shared/shared.module';
import { CostoDeliveryComponent } from './costo-delivery.component';
import { CostoDeliveryDetailComponent } from './costo-delivery-detail.component';
import { CostoDeliveryUpdateComponent } from './costo-delivery-update.component';
import { CostoDeliveryDeletePopupComponent, CostoDeliveryDeleteDialogComponent } from './costo-delivery-delete-dialog.component';
import { costoDeliveryRoute, costoDeliveryPopupRoute } from './costo-delivery.route';

const ENTITY_STATES = [...costoDeliveryRoute, ...costoDeliveryPopupRoute];

@NgModule({
  imports: [NexoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CostoDeliveryComponent,
    CostoDeliveryDetailComponent,
    CostoDeliveryUpdateComponent,
    CostoDeliveryDeleteDialogComponent,
    CostoDeliveryDeletePopupComponent
  ],
  entryComponents: [CostoDeliveryDeleteDialogComponent]
})
export class NexoCostoDeliveryModule {}
