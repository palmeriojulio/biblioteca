package br.com.pjcode.biblioteca.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private LivroService livroService;

    @PostMapping("/livro")
    public ResponseEntity<Object> save(@RequestBody @Validated LivroDto livroDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livroDto));
    }

    @GetMapping("/livros")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findAll());
    }

    @GetMapping("/livro/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findById(id));
    }

    @PutMapping("/livro/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Validated LivroDto livroDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.update(livroDto, id));
    }

    @DeleteMapping("/livro/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.delete(id));
    }
}
