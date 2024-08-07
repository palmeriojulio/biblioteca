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
          labels: ['Memórias póst...', 'O cortiço', 'Broquéis', 'Triste fim de...', 'Macunaíma'],
          datasets: [{
            label: 'Os 5 Livros Mais Emprestados',
            data: [30, 25, 15, 20, 10],
            backgroundColor: 'rgba(33, 150, 243, 0.2)',
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
