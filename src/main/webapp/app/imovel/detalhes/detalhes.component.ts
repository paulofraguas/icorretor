import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';
import { ImovelService} from '../service';
import { SuperDetalhes } from '../../comum/superdetalhes.component';





//Corretor corretor


import { CorretorService } from '../../corretor/service';



@Component({
  selector: 'app-detalhes',
  templateUrl: './detalhes.component.html',
  styleUrls: ['./detalhes.component.css']
})

export class DetalhesComponent extends SuperDetalhes implements OnInit {

  constructor(service: ImovelService, router: Router, route: ActivatedRoute, private corretorService:CorretorService) {
    super(service, router, route);
  }
  
  ngOnInit() {
    super.ngOnInit();
    this.atualizaCorretor();
  }

  listacorretors=[];

  atualizaCorretor(){
    this.corretorService.getAll().then(response=>{this.listacorretors=this.converteCorretor(response.values);});
  }

  converteCorretor(lista:any[]){
    let retorno=[];
    retorno.push({label:'Selecione', value:null});
    for(let obj of lista){
      retorno.push({label:obj.id, value:obj});
    }
    return retorno;
  }
}
