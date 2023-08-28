import { Injectable } from '@angular/core';

import jwtDecode from 'jwt-decode';
import moment from 'moment';

import { JWTInterface } from 'src/app/models/jwt';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() { }

  getDecodedAccessToken(token: string): JWTInterface | undefined {
    try {
      const decoded = jwtDecode<JWTInterface>(token);
      return decoded;
    } catch (exception: any) {
      console.log(exception);
      return undefined;
    }
  }


}
