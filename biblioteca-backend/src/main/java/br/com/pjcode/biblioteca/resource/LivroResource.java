package br.com.pjcode.biblioteca.resource;

import br.com.pjcode.biblioteca.dto.LivroDto;
import br.com.pjcode.biblioteca.service.LivroService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/biblioteca")
public class LivroResource {

    final LivroService livroService;
    public LivroResource(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/livros")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findAll());
    }
    @PostMapping("/livro")
    public ResponseEntity<Object> save(@RequestBody @Validated LivroDto livroDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livroDto));
    }
    @PutMapping("/livro/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Validated LivroDto livroDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.update(livroDto, id));
    }
    @GetMapping("/livro/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findById(id));
    }

    @DeleteMapping("/livro/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        if(Objects.isNull(livroService.findById(id))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
        } else {
            livroService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Livro deletado!");
        }
    }
}
