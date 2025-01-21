export class dashboard {

   livros!: string;
   leitores!: string;
   emprestimos!: string;
   entregasHoje!: string;
   entregasEmAtraso!: string;
   livroMaisEmprestados: string[] = [];
   leitorFaixaEtaria: string[] = [];

  constructor(
    livros: string,
    leitores: string,
    emprestimos: string,
    entregasHoje: string,
    entregasEmAtraso: string,
    livroMaisEmprestados: string[],
    leitorFaixaEtaria: string[]
  ) {
    this.livros = livros;
    this.leitores = leitores;
    this.emprestimos = emprestimos;
    this.entregasHoje = entregasHoje;
    this.entregasEmAtraso = entregasEmAtraso;
    this.livroMaisEmprestados = livroMaisEmprestados;
    this.leitorFaixaEtaria = leitorFaixaEtaria;
  }

}
