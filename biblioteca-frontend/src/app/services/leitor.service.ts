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

   constructor(private http: HttpClient, private snackBar: MatSnackBar) {
     // Inicializa a URL base concatenando com o endpoint da biblioteca
     this.localUrl = `${API}biblioteca/`;
   }

   // Método para salvar um novo leitor
   salvarLeitor(leitor: Leitor) {
     // Envia uma requisição POST para o backend com os dados do leitor
     return this.http.post(`${this.localUrl}leitor`, leitor);
   }

   // Método para listar todos os leitores
   listarLeitores(): Observable<Leitor[]> {
     // Envia uma requisição GET para obter a lista de leitores
     return this.http.get<Leitor[]>(`${this.localUrl}leitores`);
   }

   // Método para obter os detalhes de um Leitor pelo seu ID
   listarById(id: number): Observable<Leitor> {
     // Envia uma requisição GET para obter os dados de um leitor específico
     return this.http.get<Leitor>(`${this.localUrl}leitor/${id}`);
   }

   // Método para editar um Leitor existente
   editarLeitor(leitor: Leitor) {
     // Envia uma requisição PUT para atualizar os dados do Leitor
     return this.http.put(`${this.localUrl}Leitor/${leitor.id}`, leitor);
   }

   // Método para deletar um Leitor pelo seu ID
   deletarLeitor(id: number) {
     // Envia uma requisição DELETE para remover um leitor específico
     return this.http.delete(`${this.localUrl}leitor/${id}`);
   }

   // Método privado para lidar com erros de requisição HTTP
   private handleError<T>(operation = 'operation', result?: T): () => Observable<T> {
     return (): Observable<T> => {
       // Cria uma mensagem de erro para o snackbar
       const toastMessage = 'Erro ao ' + operation + '.';
       // Exibe a mensagem de erro usando MatSnackBar
       this.snackBar.open(toastMessage, 'X');

       if (!result) {
         // Se não há um resultado padrão, lança um erro
         return throwError(new Error());
       }
       // Retorna um resultado padrão para manter a aplicação funcionando
       return of(result as T);
     };
   }
}
