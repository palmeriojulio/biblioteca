import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  cards = [
    { title: 'Livros', value: '786', icon: 'book'},
    { title: 'Leitores', value: '150', icon: 'people'},
    { title: 'Emprestimos', value: '15', icon: 'book'},
    { title: 'Entregas de Hoje', value: '8', icon: 'today'},
    { title: 'Entregas em Atraso', value: '3', icon: 'schedule'}
  ];

  constructor() { }

  ngOnInit(): void {

    Chart.register(...registerables);

    const ctx = (document.getElementById('booksChart') as HTMLCanvasElement).getContext('2d');

    if (ctx) { // Verifica se ctx não é null
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: ['O Código da Inteligência', 'Pai Rico Pai Pobre', 'Como Fazer Amigos...', 'Macunaíma', 'O Senhor dos Aneis'],
          datasets: [{
            label: '5 Livros mais emprestados',
            data: [30, 25, 20, 10, 5],
            backgroundColor: 'rgba(33, 149, 243, 0.47)',
            borderColor: 'rgba(33, 150, 243, 1)',
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
  }
}
