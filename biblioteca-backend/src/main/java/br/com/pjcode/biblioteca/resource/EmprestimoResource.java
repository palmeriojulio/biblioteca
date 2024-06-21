package br.com.pjcode.biblioteca.resource;

import br.com.pjcode.biblioteca.dto.EmprestimoDto;
import br.com.pjcode.biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Palmério Júlio
 * Classe controller que recebe dados do front-end
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/biblioteca")
public class EmprestimoResource {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("/emprestimo")
    public ResponseEntity<Object> createEmprestimos(@RequestBody @Validated EmprestimoDto emprestimoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoService.save(emprestimoDto));
    }

    @GetMapping("/emprestimos")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.getAll());
    }

    @GetMapping("/emprestimo/{id}")
    public  ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.findById(id));
    }

    @PutMapping("/emprestimo/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody EmprestimoDto emprestimoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.update(emprestimoDto, id));
    }

    @DeleteMapping("/emprestimo/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.delete(id));
    }

    @PostMapping("/emprestimo/finalizar/{id}")
    public ResponseEntity<Object> devolucao(@PathVariable Long id, @RequestBody EmprestimoDto emprestimoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.finalizarEmprestimo(emprestimoDto, id));
    }
}


