--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

CREATE SEQUENCE livro_id_seq INCREMENT 1 START 1;

CREATE SEQUENCE leitor_id_seq INCREMENT 1 START 1;

CREATE SEQUENCE endereco_id_seq INCREMENT 1 START 1;

CREATE SEQUENCE emprestimo_id_seq INCREMENT 1 START 1;

CREATE TABLE public.livro {    
    id_livro bigint NOT NULL DEFAULT NEXTVAL('livro_id_seq'),
    cdu character varying(1),
    nome character varying(50),
    autor character varying(50),
    editora character varying(30)
};

CREATE TABLE public.leitor {    
    id_leitor bigint NOT NULL DEFAULT NEXTVAL('leitor_id_seq'),
    nome character varying(80),
    cpf character varying(11),
    rg character varying(20),
    data_nascimento date,
    telefone character varying(15),
    profissao character varying(50),
    escola character varying(50),
    serie character varying(10),
    curso character varying(25),    
    turno character varying(15),    
    outras_informacoes character varying(50),
    
};
