import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { SuperService } from '../comum/superservice.service';
import { AutorizadorService } from '../comum/autorizador.service';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';

@Injectable()
export class ImovelService extends SuperService {

  API_KEY: string;
  API_URL: string;

  constructor(http: Http, a: AutorizadorService) {
    super('imovels', http, a);
    this.API_KEY = 'AIzaSyA1WYuxbcU4Cop5-uUF_kCSHOpCuOPZq6Y'
    this.API_URL = `https://maps.googleapis.com/maps/api/geocode/json?key=${this.API_KEY}&address=`;
  }


  findFromAddress(place?: string, province?: string, region?: string, country?: string): Observable<any> {
    let compositeAddress = [];

    if (place) compositeAddress.push(place);
    if (province) compositeAddress.push(province);
    if (region) compositeAddress.push(region);
    if (country) compositeAddress.push(country);

    let url = `${this.API_URL}${compositeAddress.join(',')}`;
    console.log('url',url);

    return this.http.get(url).map(response => <any>response.json());
  }

}
