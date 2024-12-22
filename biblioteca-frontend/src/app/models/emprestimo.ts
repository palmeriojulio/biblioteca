import { Leitor } from "./leitor-model";
import { Livro } from "./livro-model";

export class Emprestimo {
  id?: number;
  leitor: Leitor;
  livros: Livro[] = [];
  dataDoEmprestimo?: string;
  dataDevolucaoPrevista?: string;
  dataDevolucaoReal?: string;
  status?: string;

  constructor(
    leitor: Leitor,
    livros: Livro[],
    dataDoEmprestimo?: string,
    dataDevolucaoPrevista?: string,
    dataDevolucaoReal?: string,
    status?: string
  )
  {
    this.leitor = leitor;
    this.livros = livros;
    this.dataDoEmprestimo = dataDoEmprestimo;
    this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    this.dataDevolucaoReal = dataDevolucaoReal;
    this.status = status;
  }

  /**
   * Retorna os t tulos dos livros formatados para serem exibidos em uma lista.
   * @returns {string} Uma string com os t tulos dos livros formatados para serem exibidos em uma lista.
   */
  getLivrosFormatados(): string {
    return this.livros.map(livro => livro.titulo).join("\n");
  }
}
