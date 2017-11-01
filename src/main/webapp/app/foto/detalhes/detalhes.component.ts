import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';
import { FotoService} from '../service';
import { SuperDetalhes } from '../../comum/superdetalhes.component';





//Imovel imovel


import { ImovelService } from '../../imovel/service';



@Component({
  selector: 'app-detalhes',
  templateUrl: './detalhes.component.html',
  styleUrls: ['./detalhes.component.css']
})

export class DetalhesComponent extends SuperDetalhes implements OnInit {

  constructor(service: FotoService, router: Router, route: ActivatedRoute, private imovelService:ImovelService) {
    super(service, router, route);
  }
  
  ngOnInit() {
    super.ngOnInit();
    this.atualizaImovel();
  }

  listaimovels=[];

  atualizaImovel(){
    this.imovelService.getAll().then(response=>{this.listaimovels=this.converteImovel(response.values);});
  }

  converteImovel(lista:any[]){
    let retorno=[];
    retorno.push({label:'Selecione', value:null});
    for(let obj of lista){
      retorno.push({label:obj.id, value:obj});
    }
    return retorno;
  }
}
