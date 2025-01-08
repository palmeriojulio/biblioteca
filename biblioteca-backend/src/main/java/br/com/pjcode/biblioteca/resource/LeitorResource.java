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
public class LeitorResource {
    @Autowired
    private LeitorService leitorService;

    /**
     * Salva um leitor no banco de dados.
     * @param leitorDto dados do leitor.
     * @return ResponseEntity com o leitor cadastrado.
     */
    @PostMapping("/leitor")
    public ResponseEntity<Object> save(@RequestBody @Validated LeitorDto leitorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leitorService.save(leitorDto));
    }

    /**
     * Retorna todos os leitores cadastrados no banco de dados.
     * @return ResponseEntity com a lista de leitores.
     */
    @GetMapping("/leitores")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.getAll());
    }

    /**
     * Busca um leitor no banco de dados por ID.
     * @param id o ID do Leitor a ser buscado.
     * @return ResponseEntity com o Leitor referente ao "ID" passado como parâmetro.
     * @throws ResourceNotFoundException Caso o Leitor não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @GetMapping("/leitor/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.findById(id));
    }

    /**
     * Busca um leitor no banco de dados por Nome.
     * @param nome o nome do Leitor a ser buscado.
     * @return ResponseEntity com o Leitor referente ao "Nome" passado como parâmetro.
     * @throws ResourceNotFoundException Caso o Leitor não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @GetMapping("/leitor/nome/{nome}")
    public ResponseEntity<Object> findByName(@PathVariable(value = "nome") String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.findByName(nome));
    }

    /**
     * Busca um leitor no banco de dados por CPF.
     * @param cpf o CPF do Leitor a ser buscado.
     * @return ResponseEntity com o Leitor referente ao "CPF" passado como parâmetro.
     * @throws ResourceNotFoundException Caso o Leitor não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @GetMapping("/leitor/cpf/{cpf}")
    public ResponseEntity<Object> findByCpf(@PathVariable(value = "cpf") String cpf) {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.findByCpf(cpf));
    }

    /**
     * Atualiza os dados de um leitor no banco de dados.
     * @param id o ID do Leitor a ser atualizado.
     * @param leitorDto o objeto contendo os dados do Leitor a serem atualizados.
     * @return ResponseEntity com o Leitor atualizado.
     * @throws ResourceNotFoundException Caso o Leitor não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @PutMapping("/leitor/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Validated LeitorDto leitorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leitorService.update(leitorDto, id));
    }

    /**
     * Desativa um leitor no banco de dados.
     * @param id o ID do Leitor a ser desativado.
     * @return ResponseEntity com uma mensagem de sucesso.
     * @throws ResourceNotFoundException Caso o Leitor não seja encontrado.
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor.
     */
    @DeleteMapping("/leitor/desativar/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(leitorService.delete(id));
    }

}
