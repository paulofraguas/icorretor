import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PropostaService} from '../service';
import { SuperLista } from '../../comum/superlista.component';

@Component({
  selector: 'app-lista',
  templateUrl: '../../comum/lista.component.html',
  styleUrls: ['../../comum/lista.component.css']
})
export class ListaComponent extends SuperLista implements OnInit {

  constructor(service: PropostaService, router: Router, route: ActivatedRoute) {
    super(service, router, route);
    this.cols = [
          { field: 'valor', header: 'valor' },
          { field: 'dia', header: 'dia' },
          { field: 'observacao', header: 'observacao' },
{ field: 'imovel.id', header: 'Imovel' },




    ];
  }


}
