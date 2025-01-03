import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { API } from 'app.api';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  // Variável que recebe a URL base do backend
  localUrl: string;

 /**
  * Construtor do serviço LoginService.
  *
  * @param http - Serviço para requisições HTTP.
  * @param snackBar - Serviço para exibir mensagens na tela.
  */
  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
    this.localUrl = `${API}biolab/`;
  }

  /**
   * Faz uma requisição HTTP GET para validar a senha do usuário.
   *
   * @param login O login do usuário.
   * @param password A senha do usuário.
   * @returns Uma promessa com o resultado da requisição.
   */
  realizarLogin(login: string, password: string): Observable<any> {
    let params = { login: login, password: password };
    return this.http.get<any>(`${this.localUrl}validarSenha`, { params });
  }
}
