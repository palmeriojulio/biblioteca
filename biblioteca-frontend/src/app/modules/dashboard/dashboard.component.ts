import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { LeitorService } from 'src/app/services/leitor.service';
import { LivroService } from 'src/app/services/livro.service';
import { EmprestimoService } from 'src/app/services/emprestimo.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  livros: any;
  leitores: any;
  cards: Array<{title: string, value: string, icon: string}> = [];

  constructor(
    private leitorService: LeitorService, // Serviço para manipulação de leitores
    private livroService: LivroService, // Serviço para manipulação de livros
    private emprestimoService: EmprestimoService
  ) { }

  ngOnInit(): void {
    this.emprestimoService.dashboard().subscribe((res:any) => {
      this.cards = [
        { title: 'Livros', value: res.livros, icon: 'book'},
        { title: 'Leitores', value: res.leitores, icon: 'person'},
        { title: 'Emprestimos', value: res.emprestimos, icon: 'book'},
        { title: 'Entregas de Hoje', value: res.entregasHoje, icon: 'today'},
        { title: 'Em Atraso', value: res.entregasEmAtraso, icon: 'schedule'}
      ];
    })



    Chart.register(...registerables);

    const ctx = (document.getElementById('booksChart') as HTMLCanvasElement).getContext('2d');
    const doughnutChart = (document.getElementById('doughnutChart') as HTMLCanvasElement).getContext('2d');

    if (ctx) { // Verifica se ctx não é null
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: ['O Código da Inteligência', 'Pai Rico Pai Pobre', 'Como Fazer Amigos...', 'Macunaíma', 'O Senhor dos Aneis'],
          datasets: [{
            label: 'Livros mais emprestados',
            data: [30, 25, 20, 10, 5],
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
          labels: ['Livros com atraso'],
          datasets: [{
            label: 'Livros em atraso',
            data: [30, 25, 20, 10, 5],
            backgroundColor: [
              'rgba(255, 200, 70, 0.76)',
              'rgba(75, 192, 192, 0.75)',
              'rgba(54, 163, 235, 0.83)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(201, 203, 207, 0.2)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
        }
      });
    }
  }

  listarLivros() {
    this.livroService.listarLivros().subscribe((res: any) => {
      this.livros = res.length;
    });
  }
}
