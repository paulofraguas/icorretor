import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FotoService} from '../service';
import { SuperLista } from '../../comum/superlista.component';

@Component({
  selector: 'app-lista',
  templateUrl: '../../comum/lista.component.html',
  styleUrls: ['../../comum/lista.component.css']
})
export class ListaComponent extends SuperLista implements OnInit {

  constructor(service: FotoService, router: Router, route: ActivatedRoute) {
    super(service, router, route);
    this.cols = [
          { field: 'descricao', header: 'descricao' },
          { field: 'imagem', header: 'imagem' },
{ field: 'imovel.id', header: 'Imovel' },




    ];
  }


}
