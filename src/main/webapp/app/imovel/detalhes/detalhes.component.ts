import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';
import { ImovelService } from '../service';
import { SuperDetalhes } from '../../comum/superdetalhes.component';





//Corretor corretor


import { CorretorService } from '../../corretor/service';



@Component({
  selector: 'app-detalhes',
  templateUrl: './detalhes.component.html',
  styleUrls: ['./detalhes.component.css']
})

export class DetalhesComponent extends SuperDetalhes implements OnInit {

  title: string = 'My first AGM project';
  lat: number = -23.4301026;
  lng: number = -51.94021499999999;
  zoom: number =14;


  constructor(service: ImovelService, router: Router, route: ActivatedRoute, private corretorService: CorretorService) {
    super(service, router, route);
  }

  ngOnInit() {
    super.ngOnInit();
    this.atualizaCorretor();
  }

  listacorretors = [];

  atualizaCorretor() {
    this.corretorService.getAll().then(response => { this.listacorretors = this.converteCorretor(response.values); });
  }

  converteCorretor(lista: any[]) {
    let retorno = [];
    retorno.push({ label: 'Selecione', value: null });
    for (let obj of lista) {
      retorno.push({ label: obj.id, value: obj });
    }
    return retorno;
  }

  changeEndereco(evento){
    this.recuperado();
  }

  recuperado() {
    this.service
      .findFromAddress(this.selecionado['endereco'], 'Maringa', 'Parana', 'Brasil')
      .subscribe(response => {
        if (response.status == 'OK') {
          this.lat = response.results[0].geometry.location.lat;
          this.lng = response.results[0].geometry.location.lng;
          this.selecionado['latitude']=this.lat;
          this.selecionado['longitude']=this.lng;
        } else if (response.status == 'ZERO_RESULTS') {
          console.log('geocodingAPIService', 'ZERO_RESULTS', response.status);
        } else {
          console.log('geocodingAPIService', 'Other error', response.status);
        }
      });
  }

}
