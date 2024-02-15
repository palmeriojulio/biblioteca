package br.com.pjcode.biblioteca.resource;

import br.com.pjcode.biblioteca.dto.LeitorDto;
import br.com.pjcode.biblioteca.service.LeitorService;
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
public class LeitorResoucer {
    @Autowired
    LeitorService leitorService;

    @GetMapping("/leitores")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.getAll());
    }
    @PostMapping("/leitor")
    public ResponseEntity<Object> save(@RequestBody @Validated LeitorDto leitorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leitorService.save(leitorDto));
    }
    @PutMapping("/leitor/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Validated LeitorDto leitorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leitorService.update(leitorDto, id));
    }
    @GetMapping("/leitor/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.findById(id));
    }
    @GetMapping("/leitor/nome/{nome}")
    public ResponseEntity<Object> findByName(@PathVariable(value = "nome") String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.findByName(nome));
    }
    @GetMapping("/leitor/cpf/{cpf}")
    public ResponseEntity<Object> findByCpf(@PathVariable(value = "cpf") String cpf) {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.findByCpf(cpf));
    }
    @DeleteMapping("/leitor/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.delete(id));
    }

}
