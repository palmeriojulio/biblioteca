import { HttpClient } from "@angular/common/http";
import { MatSnackBar } from "@angular/material/snack-bar";
import { API } from "app.api";
import { Emprestimo } from "../models/emprestimo";
import { Observable, of, throwError } from "rxjs";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {

  // Variável que recebe a URL base do backend
  localUrl: string;

  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
    // Inicializa a URL base concatenando com o endpoint da biblioteca
    this.localUrl = `${API}biblioteca/`;
  }

  salvarEmprestimo(emprestimo: Emprestimo) {
    // Envia uma requisição POST para o backend com os dados do emprestimo
    return this.http.post(`${this.localUrl}emprestimo`, emprestimo);
  }

  listarEmprestimos(): Observable<Emprestimo[]> {
    // Envia uma requisição GET para obter a lista de emprestimos
    return this.http.get<Emprestimo[]>(`${this.localUrl}emprestimos`);
  }

  listarById(id: number): Observable<Emprestimo> {
    // Envia uma requisição GET para obter os dados de um emprestimo específico
    return this.http.get<Emprestimo>(`${this.localUrl}emprestimo/${id}`);
  }

  editarEmprestimo(emprestimo: Emprestimo) {
    // Envia uma requisição PUT para atualizar os dados do emprestimo
    return this.http.put(`${this.localUrl}emprestimo/${emprestimo.id}`, emprestimo);
  }

  excluirEmprestimo(id: number) {
    // Envia uma requisição DELETE para excluir o emprestimo
    return this.http.delete(`${this.localUrl}emprestimo/${id}`);
  }

  devolucao(id: number, emprestimo: Emprestimo) {
    return this.http.post(`${this.localUrl}emprestimo/devolucao/${id}`, emprestimo);
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
