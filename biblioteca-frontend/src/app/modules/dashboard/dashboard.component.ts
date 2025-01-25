import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { LeitorService } from 'src/app/services/leitor.service';
import { LivroService } from 'src/app/services/livro.service';
import { DashboardService } from 'src/app/services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  cards: Array<{title: string, value: string, icon: string}> = [];

  constructor(
    private dashboard: DashboardService,
  ) { }

  ngOnInit(): void {

    this.dashboard.InfoDashboard().subscribe((res:any) => {
      this.cards = [
        { title: 'Livros', value: res.livros, icon: 'book'},
        { title: 'Leitores', value: res.leitores, icon: 'person'},
        { title: 'Emprestimos', value: res.emprestimos, icon: 'book'},
        { title: 'Entregas de Hoje', value: res.entregasHoje, icon: 'today'},
        { title: 'Em Atraso', value: res.entregasEmAtraso, icon: 'schedule'}
      ];

      Chart.register(...registerables);

      const ctx = (document.getElementById('booksChart') as HTMLCanvasElement).getContext('2d');
      const doughnutChart = (document.getElementById('doughnutChart') as HTMLCanvasElement).getContext('2d');

      if (ctx) { // Verifica se ctx não é null
        new Chart(ctx, {
          type: 'bar',
          data: {
            labels: res.livroMaisEmprestados.map((livro: any) => this.abreviarTitulo(livro.titulo)),
            datasets: [{
              label: 'Quantidade de empréstimos',
              data: res.livroMaisEmprestados.map((livro: any) => livro.quant),
              backgroundColor: [
                'rgba(255, 205, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(201, 203, 207, 0.2)'
              ],
              borderColor: [
                'rgb(255, 205, 86)',
                'rgb(75, 192, 192)',
                'rgb(54, 162, 235)',
                'rgb(153, 102, 255)',
                'rgb(201, 203, 207)'
              ],
              borderWidth: 1
            }]
          },
          options: {
            responsive: true,
            scales: {
              x: {
                beginAtZero: true
              },
              y: {
                beginAtZero: true
              }
            }
          }
        });
      }

      if (doughnutChart) {
        new Chart(doughnutChart, {
          type: 'doughnut',
          data: {
            labels: ['Entre 0-12', 'Entre 13-18', 'Entre 19-30', 'Entre 31-40', '+ de 41'],
            datasets: [{
              label: '',
              data: [
                res.leitoresFaixaEtaria.faixa0_12,
                res.leitoresFaixaEtaria.faixa13_18,
                res.leitoresFaixaEtaria.faixa19_30,
                res.leitoresFaixaEtaria.faixa26_40,
                res.leitoresFaixaEtaria.faixa41
              ],
              backgroundColor: [
                'rgba(255, 200, 70, 0.76)',
                'rgba(75, 192, 192, 0.75)',
                'rgba(54, 163, 235, 0.83)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(201, 203, 207, 0.2)'
              ],
              borderColor: [
                'rgba(175, 137, 46, 0.45)',
                'rgba(57, 145, 145, 0.36)',
                'rgba(32, 98, 143, 0.27)',
                'rgba(77, 50, 131, 0.2)',
                'rgba(100, 101, 104, 0.2)'
              ],
              borderWidth: 1
            }]
          },
          options: {
            responsive: true,
          }
        });
      }
    });
  }

  abreviarTitulo(titulo: string): string {
    const maxLength = 20; // Defina o comprimento máximo desejado
    return titulo.length > maxLength ? titulo.substring(0, maxLength) + '...' : titulo;
  }
}
