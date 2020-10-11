import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { NexoSharedModule } from 'app/shared/shared.module';
import { NexoCoreModule } from 'app/core/core.module';
import { NexoAppRoutingModule } from './app-routing.module';
import { NexoHomeModule } from './home/home.module';
import { NexoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { CustomModule } from './custom/custom.module';

@NgModule({
  imports: [
    BrowserModule,
    NexoSharedModule,
    NexoCoreModule,
    NexoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CustomModule,
    NexoEntityModule,
    NexoAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class NexoAppModule {}
