-- Script para creação da tabela usuario

-- Criação da tabela perfil
CREATE TABLE public.perfil (
    id_perfil BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Inserir perfis padrão
INSERT INTO public.perfil (nome) VALUES ('ADMIN'), ('USER');

-- Criação da tabela usuario
CREATE TABLE public.usuario (
    id_usuario UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Criação da tabela de associação user_perfil
CREATE TABLE public.user_perfil (
    id_usuario UUID NOT NULL,
    id_perfil BIGINT NOT NULL,
    PRIMARY KEY (id_usuario, id_perfil),
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_perfil FOREIGN KEY (id_perfil) REFERENCES public.perfil (id_perfil) ON DELETE CASCADE
);

-- Inserir usuário padrão com perfil ADMIN
INSERT INTO public.usuario (username, password) VALUES ('admin', 'senha_encriptada');
INSERT INTO public.user_perfil (id_usuario, id_perfil)
VALUES (
    (SELECT id_usuario FROM public.usuario WHERE username = 'admin'),
    (SELECT id_perfil FROM public.perfil WHERE nome = 'ADMIN')
);
