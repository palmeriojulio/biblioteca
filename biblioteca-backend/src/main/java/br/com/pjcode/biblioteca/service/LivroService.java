package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.LivroRepository;
import br.com.pjcode.biblioteca.domain.Livro;
import br.com.pjcode.biblioteca.dto.LivroDto;
import br.com.pjcode.biblioteca.service.exceptions.ConflictException;
import br.com.pjcode.biblioteca.service.exceptions.InternalServerErrorException;
import br.com.pjcode.biblioteca.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Palmério Júlio
 * Classe serviço com o CRUD de da entidade livro, trata regras de negócio e se comunica com o repositório.
 */
@Service
public class LivroService {

    final LivroRepository livroRepository;
    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    /**
     * Método para salvar um livro no banco.
     * @author Palmério Júlio
     * @param livroDto
     * @return Object com o Livro que foi cadastrada
     * @throws ConflictException
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object save(LivroDto livroDto) throws ConflictException {
        try {
            Boolean retorno = livroRepository.existsByTitulo(livroDto.getTitulo());
            if (retorno){
                throw new ConflictException("Já existe um livro com o titulo: "+livroDto.getTitulo());
            }
            var livro = livroRepository.save(LivroDto.toLivro(livroDto));
            return convertReturn(livro);
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao cadastrar os livro, entre em contato com o suporte");
        }
    }

    /**
     * Método para atualizar os dados de um livro.
     * @author Palmério Júlio
     * @param livroDto que vem do controller enviado pelo usuario.
     * @param id enviado pela url
     * @return Entity de Livro que foi atualizada no bando.
     * @throws ConflictException com mensagem de alerta de livro não encontrado.
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object update(LivroDto livroDto, Long id) {
        try {
            var livro = livroRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Livro com id: "+id+" não encontrado!"));
            BeanUtils.copyProperties(livroDto, livro, "id");
            livro = livroRepository.save(livro);
            return convertReturn(livro);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao atualizar o livro, ");
        }
    }

    /**
     * Método para buscar todos os registros dos livro já cadastrados.
     * @author Palmério Júlio
     * @return List<Livro>
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public List<LivroDto> findAll() {
        try {
            return livroRepository.findAll()
                    .stream()
                    .map(l -> LivroDto.fromLivro(l))
                    .sorted((l1, l2) -> l1.getId().compareTo(l2.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os livros");
        }
    }

    /**
     * Método para buscar um livro já cadastrado
     * @param id que vem do controller
     * @return Object com o Livro com o id que foi passado como parâmetro
     * @throws ResourceNotFoundException com mensagem de alerta de livro não encontrado.
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public Object findById(Long id) {
        try {
            var livro = livroRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Livro com id: "+id+" não encontrado!"));
            return convertReturn(livro);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao buscar o livro");
        }
    }

    @Transactional
    public Object delete(Long id) {
        try {
            if (Objects.isNull(livroRepository.findById(id))) {
                throw new ResourceNotFoundException("Livro com id: "+id+" não encontrado!");
            }
            livroRepository.deleteById(id);
            return "Livro excluído com sucesso!";
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            // Exemplo: Retornar uma mensagem específica para o cliente
            return e.getMessage();
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao deletar o livro");
        }
    }

    private LivroDto convertOptionalReturn(Optional<Livro> livro) {
        try {
            if (livro.isPresent()){
                return LivroDto.fromLivro(livro.get());
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LivroDto convertReturn(Livro livro) {
        try {
            if (Objects.nonNull(livro)) {
                return LivroDto.fromLivro(livro);
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

}
