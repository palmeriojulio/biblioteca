-- Adiciona a coluna idade
ALTER TABLE public.leitor
ADD COLUMN idade BIGINT;

-- Altera a coluna data_nascimento para aceitar data e hora
ALTER TABLE public.leitor
ALTER COLUMN data_nascimento TYPE TIMESTAMP;

