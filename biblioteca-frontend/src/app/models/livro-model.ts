export class Livro {
  id?: number;
  cdu?: number;
  titulo?: string;
  autor?: string;
  editora?: string;
  quantidadeDisponivel?: number;
  status?: string;

  constructor(id?: number, cdu?: number, titulo?: string, autor?: string, editora?: string, quantidadeDisponivel?: number, status?: string)
  {
    this.id = id;
    this.cdu = cdu;
    this.titulo = titulo;
    this.autor = autor;
    this.editora = editora;
    this.quantidadeDisponivel = quantidadeDisponivel;
    this.status = status;
  }
}
