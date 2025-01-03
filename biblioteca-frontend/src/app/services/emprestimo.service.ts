import { HttpClient } from "@angular/common/http";
import { MatSnackBar } from "@angular/material/snack-bar";
import { API } from "app.api";
import { Emprestimo } from "../models/emprestimo";
import { Observable, of, throwError } from "rxjs";
import { Injectable } from "@angular/core";
import { Livro } from "../models/livro-model";

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {

  // Variável que recebe a URL base do backend
  localUrl: string;

  /**
   * Construtor do serviço de empréstimos.
   *
   * @param http - Serviço para requisições HTTP.
   * @param snackBar - Serviço para exibir mensagens na tela.
   */
  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
    // Inicializa a URL base concatenando com o endpoint da api
    this.localUrl = `${API}biblioteca/`;
  }

  /**
   * Envia uma requisição POST para o backend com os dados do emprestimo.
   *
   * @param emprestimo O emprestimo a ser salvo.
   * @returns Uma promessa com o resultado da requisição.
   */
  salvarEmprestimo(emprestimo: Emprestimo) {
    return this.http.post(`${this.localUrl}emprestimo`, emprestimo);
  }

  /**
   * Envia uma requisição GET para o backend para obter a lista de emprestimos.
   *
   * @returns Uma promessa com a lista de emprestimos.
   */
  listarEmprestimos(): Observable<Emprestimo[]> {
    return this.http.get<Emprestimo[]>(`${this.localUrl}emprestimos`);
  }

  /**
   * Envia uma requisição GET para o backend para obter os dados de um emprestimo específico.
   *
   * @param id O ID do emprestimo a ser buscado.
   * @returns Uma promessa com o emprestimo buscado.
   */
  listarEmprestimosById(id: number): Observable<Emprestimo> {
    return this.http.get<Emprestimo>(`${this.localUrl}emprestimo/${id}`);
  }

  /**
   * Envia uma requisição PUT para o backend para atualizar os dados de um emprestimo específico.
   *
   * @param emprestimo O emprestimo a ser atualizado.
   * @returns Uma promessa com o resultado da requisição.
   */
  editarEmprestimo(emprestimo: Emprestimo) {
    return this.http.put(`${this.localUrl}emprestimo/${emprestimo.id}`, emprestimo);
  }

  /**
   * Envia uma requisição DELETE para o backend para excluir um emprestimo específico.
   *
   * @param id O ID do emprestimo a ser excluído.
   * @returns Uma promessa com o resultado da requisição.
   */
  excluirEmprestimo(id: number) {
    return this.http.delete(`${this.localUrl}emprestimo/${id}`);
  }

  /**
   * Realiza a devolução de um emprestimo.
   * Envia uma requisição POST para o backend com os dados do emprestimo.
   * @param id O ID do emprestimo a ser devolvido.
   * @param emprestimo O emprestimo a ser devolvido.
   * @returns Uma promessa com o resultado da requisição.
   */
  devolucao(id: number, emprestimo: Emprestimo) {
    return this.http.post(`${this.localUrl}emprestimo/devolucao/${id}`, emprestimo);
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
