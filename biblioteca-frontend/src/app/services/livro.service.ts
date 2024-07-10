import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable, throwError, of } from 'rxjs';

import { API } from 'app.api';
import { Livro } from '../models/livro-model';

@Injectable({
  providedIn: 'root'
})
export class LivroService {

  // Variável que recebe a URL base do backend
  localUrl: string;

  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
    // Inicializa a URL base concatenando com o endpoint da biblioteca
    this.localUrl = `${API}biblioteca/`;
  }

  // Método para salvar um novo livro
  salvarLivro(livro: Livro) {
    // Envia uma requisição POST para o backend com os dados do livro
    return this.http.post(`${this.localUrl}livro`, livro);
  }

  // Método para listar todos os livros
  listarLivros(): Observable<Livro[]> {
    // Envia uma requisição GET para obter a lista de livros
    return this.http.get<Livro[]>(`${this.localUrl}livros`);
  }

  // Método para obter os detalhes de um livro pelo seu ID
  listarById(id: number): Observable<Livro> {
    // Envia uma requisição GET para obter os dados de um livro específico
    return this.http.get<Livro>(`${this.localUrl}livro/${id}`);
  }

  // Método para editar um livro existente
  editarLivro(livro: Livro) {
    // Envia uma requisição PUT para atualizar os dados do livro
    return this.http.put(`${this.localUrl}livro/${livro.id}`, livro);
  }

  // Método para deletar um livro pelo seu ID
  deletarLivro(id: number) {
    // Envia uma requisição DELETE para remover um livro específico
    return this.http.delete(`${this.localUrl}livro/${id}`);
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
