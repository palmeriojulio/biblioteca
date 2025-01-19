package br.com.pjcode.biblioteca.resource;

import br.com.pjcode.biblioteca.dto.DashboardDto;
import br.com.pjcode.biblioteca.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/biblioteca")
public class DashboardResource {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDto> populaDashboard() {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.popularDashboard());
    }
}
