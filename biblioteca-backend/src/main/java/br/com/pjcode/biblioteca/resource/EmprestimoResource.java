package br.com.pjcode.biblioteca.resource;

import br.com.pjcode.biblioteca.dto.EmprestimoDto;
import br.com.pjcode.biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    /**
     * Cria um novo empréstimo a partir do emprestimoDto fornecido.
     * @param emprestimoDto o emprestimo a ser criado
     * @return um ResponseEntity com o emprestimo criado
     */
    @PostMapping("/emprestimo")
    public ResponseEntity<Object> createEmprestimos(@RequestBody @Validated EmprestimoDto emprestimoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoService.save(emprestimoDto));
    }

    /**
     * Busca todos os empréstimos realizados.
     * @return ResponseEntity com lista de empréstimos realizados
     */
    @GetMapping("/emprestimos")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.getAll());
    }

    /**
     * Busca todos os empréstimos realizados com status ativo.
     * @return ResponseEntity com a lista de empréstimos ativos.
     */
    @GetMapping("/emprestimos/ativos")
    public ResponseEntity<Object> getAllStatusAtivo() {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.getAllStatusAtivo());
    }

    /**
     * Retorna a quantidade de empréstimos realizados no dia atual.
     * @return ResponseEntity com a quantidade de empréstimos realizados no dia atual.
     */
    @GetMapping("/dashboard/emprestimos/hoje")
    public ResponseEntity<Object> getAllEmprestimosHoje() {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.getAllEmprestimosHoje(LocalDateTime.now()));
    }

    /**
     * Retorna a quantidade de empréstimos atrasados na data atual.
     * @return ResponseEntity com a quantidade de empréstimos atrasados na data atual.
     */
    @GetMapping("/dashboard/emprestimos/atrasados")
    public ResponseEntity<Object> getAllEmprestimosAtrasados() {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.getAllEmprestimosAtrasados(LocalDateTime.now()));
    }

    /**
     * Busca um empréstimo realizado com o "ID" informado.
     * @param id o "ID" do empréstimo a ser buscado
     * @return ResponseEntity com o empréstimo encontrado
     */
    @GetMapping("/emprestimo/{id}")
    public  ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.findById(id));
    }

    /**
     * Atualiza um empréstimo realizado com o "ID" informado.
     * @param id o "ID" do empréstimo a ser atualizado
     * @param emprestimoDto o empréstimo a ser atualizado
     * @return ResponseEntity com o empréstimo atualizado
     */
    @PutMapping("/emprestimo/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody EmprestimoDto emprestimoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.update(emprestimoDto, id));
    }

    /**
     * Deleta um empréstimo realizado com o "ID" informado.
     * @param id o "ID" do empréstimo a ser deletado
     * @return ResponseEntity com uma mensagem caso o empréstimo tenho sido deletado
     */
    @DeleteMapping("/emprestimo/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.delete(id));
    }

    /**
     * Finaliza a devolução de um empréstimo de livro.
     * Atualiza o status do empréstimo e dos livros, e salva as alterações no banco de dados.
     *
     * @param id o "ID" do empréstimo a ser finalizado na devolução
     * @param emprestimoDto o objeto contendo os detalhes do empréstimo a ser devolvido
     * @return ResponseEntity com o empréstimo finalizado
     * @throws ResourceNotFoundException Caso o empréstimo não seja encontrado
     * @throws ConflictException Caso o empréstimo já tenha sido devolvido anteriormente
     * @throws InternalServerErrorException Caso ocorra um erro interno no servidor
     */
    @PostMapping("/emprestimo/devolucao/{id}")
    public ResponseEntity<Object> devolucao(@PathVariable Long id, @RequestBody EmprestimoDto emprestimoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.finalizarEmprestimo(emprestimoDto, id));
    }
}


