import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersona } from 'app/shared/model/persona.model';

@Component({
  selector: 'jhi-persona-detail',
  templateUrl: './persona-detail.component.html'
})
export class PersonaDetailComponent implements OnInit {
  persona: IPersona;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ persona }) => {
      this.persona = persona;
    });
  }

  previousState() {
    window.history.back();
  }
}
