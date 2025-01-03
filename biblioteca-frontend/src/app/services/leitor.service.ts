import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { API } from 'app.api';
import { Observable, throwError, of } from 'rxjs';
import { Leitor } from '../models/leitor-model';

@Injectable({
  providedIn: 'root'
})
export class LeitorService {

   // Variável que recebe a URL base do backend
   localUrl: string;

  /**
   * Construtor do serviço LeitorService.
   *
   * @param http - Serviço o para requisições em HTTP.
   * @param snackBar - Serviço o para exibir mensagens na tela.
   */
   constructor(private http: HttpClient, private snackBar: MatSnackBar) {
     // Inicializa a URL base concatenando com o endpoint da api
     this.localUrl = `${API}biblioteca/`;
   }

   /**
    * Envia uma requisição o POST para o backend com os dados do leitor.
    * @param leitor O leitor a ser salvo.
    * @returns Uma promessa com o resultado da requisição.
    */
   salvarLeitor(leitor: Leitor) {
     return this.http.post(`${this.localUrl}leitor`, leitor);
   }

   /**
    * Envia uma requisição GET para obter a lista de leitores.
    *
    * @returns Um Observable contendo a lista de leitores.
    */
   listarLeitores(): Observable<Leitor[]> {
     return this.http.get<Leitor[]>(`${this.localUrl}leitores`);
   }

   /**
    * Envia uma requisição GET para obter os dados de um leitor específico.
    *
    * @param id O ID do leitor a ser buscado.
    * @returns Um Observable contendo o leitor buscado.
    */
   listarById(id: number): Observable<Leitor> {
     return this.http.get<Leitor>(`${this.localUrl}leitor/${id}`);
   }

   /**
    * Envia uma requisição PUT para atualizar os dados de um leitor específico.
    *
    * @param leitor O leitor a ser atualizado.
    * @returns Um Observable com o resultado da requisição.
    */
   editarLeitor(leitor: Leitor) {
     return this.http.put(`${this.localUrl}leitor/${leitor.id}`, leitor);
   }

   /**
    * Envia uma requisição DELETE para desativar um leitor específico.
    * Isso remove o leitor da lista de leitores ativos.
    *
    * @param id O ID do leitor a ser desativado.
    * @returns Um Observable com o resultado da requisição.
    */
   desativarLeitor(id: number) {
     return this.http.delete(`${this.localUrl}leitor/desativar/${id}`);
   }

   /**
    * Função privada para lidar com erros de requisição HTTP.
    * Cria uma mensagem de erro para o snackbar e a exibe.
    * Se não há um resultado padrão, lança um erro.
    * Caso contrário, retorna um resultado padrão para manter a aplicação funcionando.
    *
    * @param operation O nome da operação que falhou.
    * @param result O resultado padrão a ser retornado, caso seja fornecido.
    * @returns Uma função que retorna uma promessa com o resultado padrão.
    */
   private handleError<T>(operation = 'operation', result?: T): () => Observable<T> {
     return (): Observable<T> => {

       const toastMessage = 'Erro ao ' + operation + '.';
       this.snackBar.open(toastMessage, 'X');

       if (!result) {
         return throwError(new Error());
       }
       return of(result as T);
     };
   }
}
