package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.EmprestimoRepository;
import br.com.pjcode.biblioteca.dao.LeitorRepository;
import br.com.pjcode.biblioteca.dao.LivroRepository;
import br.com.pjcode.biblioteca.dto.DashboardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService {

    @Autowired private EmprestimoService emprestimoService;
    @Autowired private LeitorService leitorService;
    @Autowired private LivroService livroService;

    public DashboardDto popularDashboard() {
        var dash = new DashboardDto();

        dash.setLivros(livroService.countAllLivros());
        dash.setLeitores(leitorService.countLeitores());
        dash.setEmprestimos(emprestimoService.countAllEmprestimos());
        dash.setEntregasHoje(emprestimoService.countEmprestimosHoje(LocalDateTime.now()));
        dash.setEntregasEmAtraso(emprestimoService.countEmprestimosAtrasados());
        dash.setLivroMaisEmprestados(livroService.livrosMaisEmprestados());

        return dash;
    }

}
