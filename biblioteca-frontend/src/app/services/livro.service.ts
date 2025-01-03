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

  /**
   * Construtor do serviço LivroService.
   *
   * @param http - Serviço o para requisições HTTP.
   * @param snackBar - Serviço o para exibir mensagens na tela.
   */
  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
    // Inicializa a URL base concatenando com o endpoint da api
    this.localUrl = `${API}biblioteca/`;
  }

  /**
   * Envia uma requisição POST para o backend com os dados do livro.
   *
   * @param livro O livro a ser salvo.
   * @returns Uma promessa com o resultado da requisição.
   */
  salvarLivro(livro: Livro) {
    return this.http.post(`${this.localUrl}livro`, livro);
  }

  /**
   * Envia uma requisição GET para obter a lista de livros.
   *
   * @returns Uma promessa com a lista de livros.
   */
  listarLivros(): Observable<Livro[]> {
    return this.http.get<Livro[]>(`${this.localUrl}livros`);
  }

  /**
   * Envia uma requisição GET para obter a lista de livros disponíveis.
   *
   * @returns Uma promessa com a lista de livros disponíveis.
   */
  listarLivrosDisponiveis(): Observable<Livro[]> {
    return this.http.get<Livro[]>(`${this.localUrl}livros/disponiveis`);
  }

  /**
   * Envia uma requisição GET para obter os dados de um livro específico.
   *
   * @param id O ID do livro a ser buscado.
   * @returns Uma promessa com o livro buscado.
   */
  listarById(id: number): Observable<Livro> {
    return this.http.get<Livro>(`${this.localUrl}livro/${id}`);
  }

  /**
   * Envia uma requisição PUT para atualizar os dados de um livro específico.
   *
   * @param livro O livro a ser atualizado.
   * @returns Uma promessa com o resultado da requisição.
   */
  editarLivro(livro: Livro) {
    return this.http.put(`${this.localUrl}livro/${livro.id}`, livro);
  }

  /**
   * Envia uma requisição DELETE para remover um livro específico.
   *
   * @param id O ID do livro a ser deletado.
   * @returns Uma promessa com o resultado da requisição.
   */
  deletarLivro(id: number) {
    return this.http.delete(`${this.localUrl}livro/${id}`);
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
