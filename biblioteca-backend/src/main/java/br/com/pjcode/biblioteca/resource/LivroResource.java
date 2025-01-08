package br.com.pjcode.biblioteca.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.com.pjcode.biblioteca.dto.LivroDto;
import br.com.pjcode.biblioteca.service.LivroService;

/**
 * @author Palmério Júlio
 * Classe controller que recebe dados do front-end
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/biblioteca")
public class LivroResource {

    // Injeção de dependência
    @Autowired
    private LivroService livroService;

    /**
     * Cadastra um livro no banco de dados.
     * @param livroDto
     * @return ResponseEntity com o livro cadastrado.
     * @throws Exception
     */
    @PostMapping("/livro")
    public ResponseEntity<Object> save(@RequestBody @Validated LivroDto livroDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livroDto));
    }

    /**
     * Retorna todos os livros cadastrados no banco de dados.
     * @return ResponseEntity com a lista de livros.
     */
    @GetMapping("/livros")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.getAll());
    }

    /**
     * Retorna todos os livros cadastrados no banco de dados que possuem ao menos uma unidade disponível.
     * @return ResponseEntity com a lista de livros.
     */
    @GetMapping("/livros/disponiveis")
    public ResponseEntity<Object> findAllByDisponiveis() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findAllByDisponiveis());
    }

    /**
     * Busca um livro no banco de dados por ID.
     * @param id o ID do Livro a ser buscado.
     * @return ResponseEntity com o Livro referente ao "ID" passado como parâmetro.
     * @throws ResourceNotFoundException Caso o Livro não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @GetMapping("/livro/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findById(id));
    }

    /**
     * Busca um livro no banco de dados por CDU.
     * @param cdu o CDU do Livro a ser buscado.
     * @return ResponseEntity com o Livro referente ao "CDU" passado como parâmetro.
     * @throws ResourceNotFoundException Caso o Livro não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @GetMapping("/livro/cdu/{cdu}")
    public ResponseEntity<Object> findByCdu(@PathVariable (value = "cdu") String cdu) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findByCdu(cdu));
    }

    /**
     * Busca um livro no banco de dados por Título.
     * @param titulo o Título do Livro a ser buscado.
     * @return ResponseEntity com o Livro referente ao "Título" passado como parâmetro.
     * @throws ResourceNotFoundException Caso o Livro não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @GetMapping("/livro/titulo/{titulo}")
    public ResponseEntity<Object> finfByTitulo(@PathVariable (value = "titulo") String titulo) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findByTitulo(titulo));
    }

    /**
     * Atualiza um Livro no banco de dados.
     * @param id o ID do Livro a ser atualizado.
     * @param livroDto o objeto com os dados do Livro a ser atualizado.
     * @return ResponseEntity com o Livro atualizado.
     * @throws ResourceNotFoundException Caso o Livro não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @PutMapping("/livro/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Validated LivroDto livroDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.update(livroDto, id));
    }

    /**
     * Remove um Livro do banco de dados.
     * @param id o ID do Livro a ser removido.
     * @return ResponseEntity com uma mensagem de sucesso.
     * @throws ResourceNotFoundException Caso o Livro não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @DeleteMapping("/livro/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.delete(id));
    }
}
