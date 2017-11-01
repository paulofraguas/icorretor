import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ImovelService} from '../service';
import { SuperLista } from '../../comum/superlista.component';

@Component({
  selector: 'app-lista',
  templateUrl: '../../comum/lista.component.html',
  styleUrls: ['../../comum/lista.component.css']
})
export class ListaComponent extends SuperLista implements OnInit {

  constructor(service: ImovelService, router: Router, route: ActivatedRoute) {
    super(service, router, route);
    this.cols = [
          { field: 'codigoSub100', header: 'codigoSub100' },
          { field: 'endereco', header: 'endereco' },
          { field: 'bairro', header: 'bairro' },
          { field: 'descricao', header: 'descricao' },
          { field: 'tipo', header: 'tipo' },
          { field: 'preco', header: 'preco' },
          { field: 'areaConstruida', header: 'areaConstruida' },
          { field: 'areaTerreno', header: 'areaTerreno' },
          { field: 'observacoes', header: 'observacoes' },
{ field: 'foto.', header: 'Foto' },
{ field: 'proposta.', header: 'Proposta' },
{ field: 'corretor.id', header: 'Corretor' },




    ];
  }


}
