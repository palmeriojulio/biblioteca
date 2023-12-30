package br.com.pjcode.biblioteca.resourse;

import br.com.pjcode.biblioteca.dto.LivroDto;
import br.com.pjcode.biblioteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/biblioteca")
public class LivroResourse {

    final LivroService livroService;
    public LivroResourse(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/livros")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findAll());
    }
    @PostMapping("/livro")
    public ResponseEntity<Object> save(@RequestBody @Validated LivroDto livroDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livroDto));
    }


}
