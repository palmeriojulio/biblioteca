--
-- PostgreSQL database dump
--
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SEQUENCE livro_id_seq INCREMENT 1 START 1;
CREATE SEQUENCE leitor_id_seq INCREMENT 1 START 1;
CREATE SEQUENCE emprestimo_id_seq INCREMENT 1 START 1;
CREATE SEQUENCE endereco_id_seq INCREMENT 1 START 1;
CREATE SEQUENCE reserva_id_seq INCREMENT 1 START 1;

CREATE TABLE public.endereco (
    id_endereco BIGINT NOT NULL DEFAULT NEXTVAL('endereco_id_seq'),
    logradouro VARCHAR(255),
    numero VARCHAR(10),
    bairro VARCHAR(255),
    cidade VARCHAR(100),
    uf VARCHAR(10),
    PRIMARY KEY (id_endereco)
);
CREATE TABLE public.leitor (
    id_leitor BIGINT NOT NULL DEFAULT NEXTVAL('leitor_id_seq'),
    nome VARCHAR(100),
    cpf VARCHAR(11),
    rg VARCHAR(20),
    data_nascimento DATE,
    telefone VARCHAR(15),
    profissao VARCHAR(50),
    escola VARCHAR(50),
    serie VARCHAR(10),
    curso VARCHAR(25),
    turno VARCHAR(15),
    id_endereco BIGINT,
    PRIMARY KEY (id_leitor),
    FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco)
);

CREATE TABLE public.livro (
    id_livro BIGINT NOT NULL DEFAULT NEXTVAL('leitor_id_seq'),
    cdu VARCHAR(15),
    titulo VARCHAR(80),
    autor VARCHAR(80),
    editora VARCHAR(50),
    quantidade_disponivel BIGINT,
    PRIMARY KEY (id_livro)
);


CREATE TABLE public.emprestimo (
    id_emprestimo BIGINT NOT NULL DEFAULT NEXTVAL('emprestimo_id_seq'),
    id_leitor BIGINT NOT NULL,
    data_do_emprestimo DATE,
    data_da_devolucao DATE,
    status_do_emprestimo VARCHAR(50),
    PRIMARY KEY (id_emprestimo),
    FOREIGN KEY (id_leitor) REFERENCES leitor(id_leitor)
);

CREATE TABLE public.reserva (
    id_reserva BIGINT NOT NULL DEFAULT NEXTVAL('reserva_id_seq'),
    id_leitor BIGINT NOT NULL,
    id_livro BIGINT NOT NULL,
    data_reserva DATE,
    status VARCHAR(20),
    PRIMARY KEY (id_reserva),
    FOREIGN KEY (id_leitor) REFERENCES leitor(id_leitor),
    FOREIGN KEY (id_livro) REFERENCES livro(id_livro)
);

CREATE TABLE public.emprestimo_livro (
    id_emprestimo BIGINT NOT NULL,
    id_livro BIGINT NOT NULL,
    PRIMARY KEY (id_emprestimo, id_livro),
    FOREIGN KEY (id_emprestimo) REFERENCES emprestimo(id_emprestimo),
    FOREIGN KEY (id_livro) REFERENCES livro(id_livro)
);