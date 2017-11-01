import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ImovelService } from '../service';
import { SuperLista } from '../../comum/superlista.component';

@Component({
  selector: 'app-lista',
  templateUrl: 'lista.component.html',
  styleUrls: ['lista.component.css']
})
export class ListaComponent extends SuperLista implements OnInit {

  title: string = 'My first AGM project';
  lat: number = -23.4301026;
  lng: number = -51.94021499999999;
  zoom:number=14;


  constructor(service: ImovelService, router: Router, route: ActivatedRoute) {
    super(service, router, route);
    this.cols = [
      { field: 'descricao', header: 'descricao' },
      { field: 'endereco', header: 'endereco' },
      { field: 'preco', header: 'preco' }
    ];
  }

  clickedMarker(id){
    this.edita(id);
  }


}
