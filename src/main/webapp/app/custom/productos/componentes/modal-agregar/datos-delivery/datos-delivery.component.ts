import { Component, Input, OnInit } from '@angular/core';
import { IPais } from '../../../../../shared/model/pais.model';
import { PaisService } from '../../../../../entities/pais/pais.service';
import { RegionService } from '../../../../../entities/region/region.service';
import { ComunaService } from '../../../../../entities/comuna/comuna.service';
import { IRegion } from '../../../../../shared/model/region.model';
import { IComuna } from '../../../../../shared/model/comuna.model';

@Component({
  selector: 'jhi-datos-delivery',
  templateUrl: './datos-delivery.component.html',
  styles: []
})
export class DatosDeliveryComponent implements OnInit {
  @Input() paises: IPais[];
  regiones: IRegion[] = [];
  comunas: IComuna[] = [];

  constructor(protected paisService: PaisService, protected regionService: RegionService, protected comunaService: ComunaService) {}

  ngOnInit() {
    this.loadRegion(1);
    this.regionChange(1);
  }

  paisChange(idPais: number) {
    this.loadRegion(idPais);
    this.comunas = [];
  }

  /* consulta las regiones del pais */
  loadRegion(idPais: number) {
    this.regionService.getByPais(idPais).subscribe((regions: IRegion[]) => {
      this.regiones = regions;
    });
  }

  /* Consulta las regiones */
  regionChange(idRegion: number) {
    this.loadComunas(idRegion);
  }

  /* carga las comunas de la region */
  loadComunas(idRegion: number) {
    this.comunaService.getByRegion(idRegion).subscribe((res: IComuna[]) => {
      this.comunas = res;
    });
  }
}
