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
    // Inicializa a URL base concatenando com o endpoint da biblioteca
    this.localUrl = `${API}biolab/`;
  }

  realizarLogin(login: string, password: string): Observable<any> {
    // Cria um objeto de parâmetros com as credenciais do usuário
    let params = { login: login, password: password };
    // Faz uma requisição HTTP GET para validar a senha, passando os parâmetros de login
    return this.http.get<any>(`${this.localUrl}validarSenha`, { params });
  }
}
