import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IComuna, Comuna } from 'app/shared/model/comuna.model';
import { ComunaService } from './comuna.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';

@Component({
  selector: 'jhi-comuna-update',
  templateUrl: './comuna-update.component.html'
})
export class ComunaUpdateComponent implements OnInit {
  isSaving: boolean;

  regions: IRegion[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    regionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected comunaService: ComunaService,
    protected regionService: RegionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ comuna }) => {
      this.updateForm(comuna);
    });
    this.regionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRegion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRegion[]>) => response.body)
      )
      .subscribe((res: IRegion[]) => (this.regions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(comuna: IComuna) {
    this.editForm.patchValue({
      id: comuna.id,
      nombre: comuna.nombre,
      regionId: comuna.regionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const comuna = this.createFromForm();
    if (comuna.id !== undefined) {
      this.subscribeToSaveResponse(this.comunaService.update(comuna));
    } else {
      this.subscribeToSaveResponse(this.comunaService.create(comuna));
    }
  }

  private createFromForm(): IComuna {
    return {
      ...new Comuna(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      regionId: this.editForm.get(['regionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComuna>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackRegionById(index: number, item: IRegion) {
    return item.id;
  }
}
